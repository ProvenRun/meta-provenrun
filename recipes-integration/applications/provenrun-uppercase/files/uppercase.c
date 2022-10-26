// SPDX-License-Identifier: GPL-2.0-only
/*
 * Copyright (c) 2016-2022, ProvenRun S.A.S
 */
/*
 * File: uppercase.c
 * 2016-06-16: V.Siles   : Created.
 * 2018-12-24: A.Ulitskiy: Modified for using pnc_session_xxxx() API.
 */

#include <errno.h>
#include <fcntl.h>
#include <inttypes.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/ioctl.h>
#include <sys/mman.h>
#include <unistd.h>

#include <provencore.h>

#include "accelize/drmc.h"


#define DRM_LICENSING_ERROR_MSG \
"******************************** WARNING *************************************\n\
* DRM licensing issue: secured application might not be able to operate       *\n\
* normally.                                                                   *\n\
*******************************************************************************\n\n"

void print_error(int error)
{
    int sys_err = errno;
    fprintf(stderr, "Failed: error=%d, errno=%d (%s)\n",
           error, sys_err, strerror(sys_err));
}

void drm_error_callback(const char* errmsg, void* user_p)
{
    (void)errmsg;
    (void)user_p;
}

int main(int argc, char **argv)
{
    int error;
    int drm_error = DRM_OK;
    pnc_session_t * session = NULL;
    DrmManager *pDrmManager = NULL;

    if (argc <= 1) {
        printf("usage: %s <low-case-string-to-process>'\n", argv[0]);
        printf("       converts the string to uppercase by calling the\n");
        printf("       secure app 'toupper'\n");
        return EXIT_SUCCESS;
    }

    char *word = argv[1];
    size_t size = strlen(word)+1;

    if (size >= 256) {
        fprintf(stderr, "Input string is too long, limit is 255 characters.\n");
        return EXIT_FAILURE;
    }

    printf("Creating session...\n");
    error = pnc_session_new(4096, &session);
    if (error) {
        print_error(error);
        return EXIT_FAILURE;
    }

    printf("Configuring session for secure application 'toupper'...\n");
    error = pnc_session_config_by_name(session, "toupper");
    if (error) {
        print_error(error);
        goto end;
    }

    // Get shared buffer parameters - its address and the size.
    // We already know that the size allocated should be enough,
    // but formally we need to get it too:
    char *data;
    error = pnc_session_getinfo(session, (void **)&data, &size);
    if (error) {
        print_error(error);
        goto end;
    }

    // Start DRM lib session
    drm_error = DrmManager_alloc(&pDrmManager,
                "/etc/xilinx_appstore/provenrun-uppercase/pnr-uppercase-conf.json",
                "/etc/xilinx_appstore/cred.json",
                NULL, NULL, drm_error_callback, NULL);
    if (drm_error != DRM_OK) {
        goto drm_free;
    }

    // Open DRM session
    drm_error = DrmManager_activate(pDrmManager, false);
    if (drm_error != DRM_OK) {
        goto drm_free;
    }

    // Copy the input string to the shared area
    strcpy(data, word);

    // Notify the secure application and wait for the answer:
    uint32_t response;
    error = pnc_session_send_request_and_wait_response(session, 42, NO_TIMEOUT, &response);
    if (error) {
        fprintf(stderr, "Could not notify the secure application.\n");
        print_error(error);
        goto drm_deactivate;
    }
    if (response != 0) {
        fprintf(stderr, "'toupper' returned r=%d.\n", response);
        goto drm_deactivate;
    }

    // Display result if existing
    if (*data) {
        // Display the result
        printf("'%s'==>'%s'\n", word, data);
    } else {
        // No result available
        drm_error = 1;
    }

drm_deactivate:
    DrmManager_deactivate(pDrmManager, false);

drm_free:
    // Close DRM session
    if (pDrmManager != NULL) {
        DrmManager_free(&pDrmManager);
    }
    if (drm_error != DRM_OK) {
        fprintf(stderr, DRM_LICENSING_ERROR_MSG);
    }
end:
    printf("Closing the session...\n");
    pnc_session_destroy(session);
    return (error + drm_error)? EXIT_FAILURE : EXIT_SUCCESS;
}
