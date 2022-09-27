# Lab 0: Environment Set Up

## 1. Install a UNIX-based operating system

I chose to install Ubuntu, a Linux distribution based on Debian and composed mostly of free and open-source software.

- First of all, I went to the [official website](https://ubuntu.com/) in order to download the .ISO file. Then with the help of [Rufus](https://rufus.ie/en/), a software that can be used to format and create bootable USB flash drives or Live USBs. I put Ubuntu on an external USB.
- Now I boot my laptop into Ubuntu installer.
- After this I choose the language, partition, location, username, password, other installation settings and I am done. Ubuntu is finally installed. After the installation I ran the command
  `$ sudo apt update && sudo apt upgrade`
  to get the latest updates for packages

## 2. Install essential tools

### GCC compiler and VS Code install

I executed the following commands:

- for gcc compiler
  `$ sudo apt install build-essential`
- for VS Code
  `$ sudo snap install --classic code`

### Git, zsh and oh-my-zsh install

In order to install zsh, we need the git package so I ran the command

`$ sudo apt-get install git`

for zsh the command

`$ sudo apt install zsh`

for oh-my-zsh the command

`sh -c "$(curl -fsSL https://raw.github.com/ohmyzsh/ohmyzsh/master/tools/install.sh)"`

### Oh-my-zsh theme

For oh my zsh configuration I changed the default theme **robbyrussell** to **awesomepanda** by accessing the _.zshrc_ file with the command

`$ sudo vi ~/.zshrc`

I navigated to the property `ZSH_THEME `and changed it to **awesomepanda**.

`ZSH_THEME="awesomepanda"`

### Oh-my-zsh plugin

I installed the **zsh-autosuggestions** plugin by running the command

`git clone https://github.com/zsh-users/zsh-autosuggestions ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-autosuggestions`

I did the same thing as with the theme I ran the command in order to access the _.zshrc_ file

`$ sudo vi ~/.zshrc `

Then I added **zsh-autosuggestions** to _plugins_ attribute

`plugins=(git zsh-autosuggestions)`

## 3. Create a Git repository

To create a git repository I ran the command

`git init`

After this I created the hello.c, README.md and Makefile files

```
touch hello.c
touch README.md
touch Makefile
```

With the help of `nano` command I edited the files.

```
nano hello.c
nano Makefile
```

**hello.c**

```
#include <stdio.h>

int main() {
	printf("Hello World! \n");
	return 0;
}
```

**Makefile**

```
hello: hello.o
	gcc hello.o -o hello

hello.o: hello.c
	gcc -c hello.c

clean:
	rm *.o hello
```

Now I can run the `make` command in order to compile **hello.c**. Running the `./hello` command will output `Hello World!`

`make clean` will remove every file with **.o** extension.

Finally, I can make a **screenshots** folder with `mkdir` command and upload the screenshots.

```
mkdir -p screenshots/01-install-unix-based-OS
cd screenshots
mkdir 02-install-essential-tools
mkdir 03-create-git-repository
```

After all this I can run the command that will add everything to be tracked by git

`git add .`

and before committing I have to configure my name and email.

```
git config --global user.name "Your Name"
git config --global user.email "youremail@yourdomain.com"
```

I can now make the first commit with the command

`git commit -m "Initial commit"`

I set the remote branch and change the main branch from master to main with the commands

```
git remote add origin
git branch -M main
```

I am ready to push the changes to the remote repository

`git push -u origin main`
