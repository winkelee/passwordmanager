# Password Manager by winkele

This is a piece of simple, local, cross-platform credential management software.

## Features:

This password manager includes the following features:
 1. Encrypting your passwords and storing your credentials locally.
 2. Supports importing/exporting credentials from **.csv** files.
 3. Adding, editing and deleting your credentials.
 4. User-friendly UI.
 5. Cross-platform functionality for Windows and Ubuntu-based distributions of Linux.

## Installation:

### Windows:

To install this program on Windows:
 1. Download the **.exe** file from the latest release.
 2. Run the instlaler and follow instructions presented by it.
 3. The installed program can be found at **C:\Program Files\passwordmanager\passwordmanager.exe**.

### Linux (Ubuntu-based):

To install this program on Linux:
 1. Download the .deb package from the latest release.
 2. Install the package via a package installer or by running the following command from the folder with the package:

      `sudo apt-get install ./passwordmanager_[current_version]_amd64.deb`
    
 3. After the installation, the program can be found on **opt/passwordmanager/bin** by the name **passwordmanager**

### Usage:

#### Adding a new credential:

To add a new credential, click the **plus** button near the searchbar and enter your website, username and password into the fields presented, then click **Add**

#### Adding a new credential - import:

To import an amount of credentials from a **.csv** file, it must be of the correct format. This means its colums should represent **website, username, password** (first, second and third colums respectively), and it must not contain **https://** or **http://**.
After checking that your credentials file is of the correct format, you can click the **plus** button and then click **Import...**. From here, navigate to the file and choose it.

#### Exporting your credentials:

**WARNING! When exporting your credentials, your exported file will store your passwords in plaintext.**

To export the credentials, click the **plus** button, then click **Export**. Your exported credentials will be in the **Documents** directory, by the name **export.csv**.

