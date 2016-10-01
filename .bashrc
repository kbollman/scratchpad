# .bashrc

# Source global definitions
if [ -f /etc/bashrc ]; then
	. /etc/bashrc
fi

# Uncomment the following line if you don't like systemctl's auto-paging feature:
# export SYSTEMD_PAGER=

# User specific aliases and functions
set -o notify noclobber ignoreeof
shopt -s cdspell checkhash checkwinsize no_empty_cmd_completion
shopt -s histappend histreedit histverify cmdhist
shopt -u mailwarn
PROMPT_COMMAND="history -a;$PROMPT_COMMAND"
alias which='type -all'
alias ls='ls --color=tty'
alias kred="mintty.exe -D -c /home/kevin.bollman/mintty.cfg --option 'BackgroundColour=32,0,0' -"
alias kgreen="mintty.exe -D -c /home/kevin.bollman/mintty.cfg --option 'BackgroundColour=0,32,32' -"
alias kblue="mintty.exe -D -c /home/kevin.bollman/mintty.cfg --option 'BackgroundColour=0,0,32' -"
alias kpurple="mintty.exe -D -c /home/kevin.bollman/mintty.cfg --option 'BackgroundColour=32,0,32' -"
alias kterm="mintty.exe -D -c /home/kevin.bollman/mintty.cfg -"
alias kmvn="mvn -D maven.test.skip=true clean package"
alias kimvn="mvn -D maven.test.skip=true clean package install"
alias kclean="mvn -o clean"
alias kdiff="svn diff --patch-compatible "
alias kip="ipconfig | grep -A 4 'Ethernet adapter Local Area Connection' | grep IPv4"
alias git-dry-run='git fetch && git diff --stat origin/master'
alias git-dry-diff='git fetch && git diff origin/master'
alias find-crlf='find . -not -type d -exec file "{}" ";" | grep CRLF'
alias git-root='if [ "`git rev-parse --show-cdup`" != "" ]; then cd `git rev-parse --show-cdup`; fi'
#git config --global alias.up '!git remote update -p; git merge --ff-only @{u}'


complete -A hostname   ssh
complete -A export     printenv
complete -A variable   export local readonly unset
complete -A enabled    builtin
#export PS1='\D{%l:%M%P}:\u@\h:\w:\#> '
export PS1='\[\e]0;\w\a\]\D{%l:%M%P}:\u@\h:\w:\#> '
export LS_COLORS='no=00:fi=00:di=04;36:ln=01;36:pi=40;33:so=01;35:bd=40;33;01:cd=40;33;01:or=01;05;37;41:mi=01;05;37;41:*.c=00;33:*.h=00;34:*.cmd=01;32:*.exe=01;32:*.cmd=01;32:*.com=01;32:*.btm=01;32:*.bat=01;32:*.tar=01;31:*.tgz=01;31:*.tbz2=01;31:*.arc=01;31:*.arj=01;31:*.taz=01;31:*.lzh=01;31:*.lha=01;31:*.zip=01;31:*.z=01;31:*.Z=01;31:*.gz=01;31:*.bz2=01;31:*.bz=01;31:*.tz=01;31:*.rpm=01;31:*.jpg=01;35:*.jpeg=01;35:*.gif=01;35:*.bmp=01;35:*.xbm=01;35:*.xpm=01;35:*.png=01;35:*.tif=01;35:*.tiff=01;35:'
export MAVEN_HOME=~/apache-maven-3.3.9 
export PATH=~/bin:/usr/pgsql-9.2/bin/:/usr/sbin/:/sbin:$MAVEN_HOME/bin:$PATH

# If this is a login shell then print a welcome.  Need the check or the newline will break scp commands.
if shopt -q login_shell; then
    ifconfig | grep -A 4 'ens32' | grep inet
    printf "\n\n"
fi


export NVM_DIR="/home/kbollman/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && . "$NVM_DIR/nvm.sh"  # This loads nvm


