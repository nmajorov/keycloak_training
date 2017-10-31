#!/bin/sh
#
# JBoss standalone control script
#
#
# description: JBoss EAP Standalone
# pidfile: EAP_HOME/jboss-eap-servername.pid


# Source function library.
. /etc/init.d/functions

# Load Java configuration.
#[ -r /etc/java/java.conf ] && . /etc/java/java.conf
#export JAVA_HOME

USAGE ()
{
  echo 'Usage : `basename $0` <jboss-home-path> <user>  <server-name>  {start|stop|status}'
  exit
}




J_SERVER_HOME=${1}

JBOSS_USER=${2}

SERVER_NAME=${3}



export J_SERVER_HOME

if [ -z "$J_SERVER_HOME" ]; then
  echo "server home dir is not specified"
  USAGE >&2
  exit 1
fi


if [ -z "$JBOSS_USER" ]; then
  echo "jboss user  name is not specified"
  USAGE >&2
  exit 1
fi

if [ -z "$SERVER_NAME" ]; then
  echo "jboss server  name is not specified"
  USAGE >&2
  exit 1
fi


if [ -z "$JBOSS_PIDFILE" ]; then
  JBOSS_PIDFILE=$J_SERVER_HOME/keycloack.pid
fi
export JBOSS_PIDFILE

if [ -z "$STARTUP_WAIT" ]; then
  STARTUP_WAIT=800 # wait longer to start
fi

if [ -z "$SHUTDOWN_WAIT" ]; then
  SHUTDOWN_WAIT=600
fi

if [ -z "$JBOSS_LOG" ]; then
  export JBOSS_LOG=$J_SERVER_HOME/${SERVER_NAME}/log/server.log
fi

# set -b option to listen on all adresses
JBOSS_SCRIPT="${J_SERVER_HOME}/bin/standalone.sh -b 0.0.0.0"

prog="keycloak server"


start() {
  echo -n "Starting $prog: "
  if [ -f $JBOSS_PIDFILE ]; then
    read ppid < $JBOSS_PIDFILE
    if [ `ps --pid $ppid 2> /dev/null | grep -c $ppid 2> /dev/null` -eq '1' ]; then
      echo -n "$prog is already running "
      failure
      echo
      return 1
    else
      rm -f $JBOSS_PIDFILE
    fi
  fi

  #check if log already exist
  if [ -f $JBOSS_LOG ];then
    mv $JBOSS_LOG  $J_SERVER_HOME/${SERVER_NAME}/log/server-$(/bin/date +%d-%m-%Y-%H-%M-%S).log
  fi

  if [ ! -z "$JBOSS_USER" ]; then
     LAUNCH_JBOSS_IN_BACKGROUND=1 JBOSS_PIDFILE=$JBOSS_PIDFILE $JBOSS_SCRIPT 2>&1 > /dev/null &
  fi

  count=0
  launched=false

  sleep 3 #let  the service time to start
  until [ $count -gt $STARTUP_WAIT ]
  do
    #
    grep 'Keycloak.*started in' $JBOSS_LOG 2> /dev/null
    if [ $? -eq 0 ] ; then
      launched=true
      break
    fi
    sleep 1
    let count=$count+1;
  done

  if [ $launched = "false" ];then
    failure
    echo
    return 1
  fi

  success
  echo
  return 0
}

stop() {
  echo -n $"Stopping $prog: "
  count=0;

  if [ -f $JBOSS_PIDFILE ]; then
    read kpid < $JBOSS_PIDFILE
    let kwait=$SHUTDOWN_WAIT

    # Try issuing SIGTERM
    kill -15 $kpid
    until [ `ps --pid $kpid 2> /dev/null | grep -c $kpid 2> /dev/null` -eq '0' ] || [ $count -gt $kwait ]
    do
      sleep 1
      let count=$count+1;
    done
    if [ $count -gt $kwait ]; then
      kill -9 $kpid
    fi
  fi
  rm -f $JBOSS_PIDFILE
  success
  echo
  return 0
}

status() {
  if [ -f $JBOSS_PIDFILE ]; then
    read ppid < $JBOSS_PIDFILE
    if [ `ps --pid $ppid 2> /dev/null | grep -c $ppid 2> /dev/null` -eq '1' ]; then
      echo "$prog is running (pid $ppid)"
      return 0
    else
      echo "$prog dead but pid file exists"
      return 1
    fi
  fi
  echo "$prog is not running"
  return 3
}

case "$4" in
  start)
      start
      exit 0
      ;;
  stop)
      stop
      exit 0
      ;;
  status)
      status
      ;;
  *)
      ## If no parameters are given, print which are available.
      echo $USAGE
      exit 1
      ;;
esac
