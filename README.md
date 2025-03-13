# PasswordManager

This is a simple, lightweight and user-friendly password manager built with Java and JavaFX.

## Features

- Password encryption and local storage.
- Import credentials from .csv files.
- User-friendly JavaFX UI.
- Cross-platform - built for Linux and Windows.

## Installation guide

### Windows:

1. Download the .exe file from the latest release.
2. Run the installer.
3. After the installation, the software will be located in **C:\Program Files\passwordmanager**, by the filename **passwordmanager.exe**.

### Linux (Ubuntu-based):

1. Download the .deb package from the latest release.
2. Install the package.
3. After the installation, the software will be located in **opt/passwordmanager/bin** by the filename **passwordmanager**.

## Usage information

### Adding a new credential (native)

To add a new credential, click the plus button near the searchbar and enter the username, password and the url into the corresponding fields. Then click **Add**.

### Adding a new credential (import)

To add a new credential via the import feature, you need a .csv file with the data you want to import.
However, first please make sure it is properly formatted:
1. The colums in the .csv file should be corresponding to **website,username,password**.
2. Columns must not contain **http://** or **https://**.

After making sure that the file is properly formatted, click on the **plus** button near the searchbar, then click on the **Import** button. Afterwards, select the file you want to export.

### Deleting and editing

The deleting and editing functions of the software can be accessed after selecting a specific credential from the list on the left, via the buttons **Edit** and **Delete**.

### Using the credentials

To copy your existing username/password, select them from the list on the left and click the button **Copy** near the field that you want to be copied to clipboard.
