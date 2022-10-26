uppercase
=========

`pnr-uppercase` is a test application for ProvenCore running in Linux. It takes as argument a lowercase word that is transmitted to a ProvenCore TA called `toupper`, this TA transforms the word to uppercase and sends it back to Linux. Linux then prints the uppercase result word.

`pnr-uppercase` is usefull to test communication between Linux and ProvenCore. It makes use of provencore driver.

`pnr-uppercase` application can be executed with a free license.

Requirements
------------

- provencore driver must be loaded in Linux
- ProvenCore must be running with `toupper` TA
- cred.json must be present in /etc/xilinx_appstore/ folder (free license credential)

Usage
-----

1- Load the PL part of the Uppercase Application once:

~~~~
sudo xmutil unloadapp
sudo xmutil loadapp provenrun-uppercase-firmware
~~~~

2- Call the app as many time as needed:

~~~~
sudo pnr-uppercase hello
~~~~

you should see an output similar to this:

~~~~
Creating session...
Configuring session for secure application 'toupper'...
'hello'==>'HELLO'
Closing the session...
~~~~
