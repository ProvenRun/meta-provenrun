uppercase
=========

`uppercase` is a test application for ProvenCore running in Linux. It takes as argument a lowercase word that is transmitted to a ProvenCore TA called `toupper`, this TA transforms the word to uppercase and sends it back to Linux. Linux then prints the uppercase result word.

`uppercase` is usefull to test communication between Linux and ProvenCore. It makes use of provencore driver.

Requirements
------------

- provencore driver must be loaded in Linux
- ProvenCore must be running with `toupper` TA

Usage
-----

~~~~
sudo uppercase hello
~~~~

you should see an output similar to this:

~~~~
Creating session...
Configuring session for secure application 'toupper'...
'hello'==>'HELLO'
Closing the session...
~~~~
