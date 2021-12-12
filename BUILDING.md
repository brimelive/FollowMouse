# Prerequisites

 - NodeJS (with NPM)
 - wget
 - Maven
 - [JDK 8](https://adoptium.net) (You _can_ use JDK 11 to compile the project since it has a built in JDK 8 mode)

## Windows
You can either
 a) Use WSL and follow the linux compilation instructions
 b) Follow these directions:
     - You will need a bash terminal, I recommend [git desktop](https://git-scm.com/). 
     - [Install NodeJS](https://nodejs.org/en/download/)  
     - [Install wget](http://gnuwin32.sourceforge.net/packages/wget.htm)
     - [Download maven, extract it somewhere, add the /bin folder to your path](https://maven.apache.org/download.cgi) (Download the binary zip)
     - Make sure to restart the bash terminal after everything gets added to your path.

## Linux
(Convert these commands to work for your packagemanager of choice, these work for most Debian based distros (like Ubuntu))
 - `sudo apt install nodejs npm`
 - `sudo apt install wget`
 - `sudo apt install maven`

## MacOS
[Install homebrew.](https://brew.sh)
 - `brew install nodejs npm`
 - `brew install wget`
 - `brew install maven`

# Note for IDEs
You will need to install [lombok](https://projectlombok.org)

# Building
1) Clone the repo
2) Open the folder in your terminal of choice (Windows users: R-Click the directory > Open Git Bash Here)
3) Open the `build` folder.
4) Run `sh build.sh` (You can optionally pass in "nocompile" or "nodist" to skip either the compilation step or build production step)
5) Profit! See `dist/` for the executables.

The build files will automatically download the runtime zips and make the executables.