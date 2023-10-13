# Setup and install Canon Pixma G1020
```bash
su
apt update
apt install -y libcupsimage2

systemctl unmask \
	cups.path \
	cups-browsed.service \
	cups.service \
	cups.socket
	
systemctl daemon-reload

systemctl enable \
	cups.path \
	cups-browsed.service \
	cups.service \
	cups.socket
	
systemctl start \
	cups.path \
	cups-browsed.service \
	cups.service \
	cups.socket
```

```bash
cd /drivers
wget https://gdlp01.c-wss.com/gds/9/0100010919/01/cnijfilter2-6.10-1-deb.tar.gz
tar xvzf cnijfilter2-6.10-1-deb.tar.gz
cd cnijfilter2-6.10-1-deb
./install.sh
```

```bash
==================================================

Canon Inkjet Printer Driver
Version 6.10
Copyright CANON INC. 2001-2020

==================================================
Command executed = sudo dpkg -iG ./packages/cnijfilter2_6.10-1_amd64.deb
(Reading database ... 324789 files and directories currently installed.)
Preparing to unpack .../cnijfilter2_6.10-1_amd64.deb ...
Unpacking cnijfilter2 (6.10-1) over (6.10-1) ...
Setting up cnijfilter2 (6.10-1) ...

#=========================================================#
#  Register Printer
#=========================================================#
Next, register the printer to the computer.
Connect the printer, and then turn on the power.
To use the printer on the network, connect the printer to the network.
When the printer is ready, press the Enter key.
> 

#=========================================================#
#  Connection Method
#=========================================================#
 1) USB
 2) Network
Select the connection method.[1]

Searching for printers...


#=========================================================#
#  Select Printer
#=========================================================#
Select the printer.
If the printer you want to use is not listed, select Update [0] to search again.
To cancel the process, enter [Q].
-----------------------------------------------------------
 0) Update
-----------------------------------------------------------
Target printers detected
1) Canon G1020 series (//Canon/?port=usb&serial=02AB90)
-----------------------------------------------------------
Currently selected:[1] Canon G1020 series (//Canon/?port=usb&serial=02AB90)
Enter the value. [1]

#=========================================================#
#  Register Printer
#=========================================================#
Enter the printer name.[G1020USB]
Command executed = sudo /usr/sbin/lpadmin -p G1020USB -P /usr/share/cups/model/canong1020.ppd -v cnijbe2://Canon/?port=usb&serial=02AB90 -E
lpadmin: Printer drivers are deprecated and will stop working in a future version of CUPS.

#=========================================================#
#  Set as Default Printer
#=========================================================#
Do you want to set this printer as the default printer?
Enter [y] for Yes or [n] for No.[y]y

#=========================================================#
Installation has been completed.
Printer Name : G1020USB
Select this printer name for printing.
#=========================================================#
```
