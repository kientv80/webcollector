#!/bin/bash

SERVICE="zlogcentral-parquet"
PORT=6971
HOST="10.30.22.64"
HOME_DIR="/zserver/java-projects/zlogcentral-parquet"

CMD_START="$HOME_DIR/runservice start production"
CMD_STOP="$HOME_DIR/runservice stop production"
PID_FILE="/zserver/tmp/$SERVICE/$SERVICE.pid"

cd $HOME_DIR


func_start() {
	echo "Starting service $SERVICE ..........."
	$CMD_START
	sleep 1
	echo "Checking .."
	PID=`cat $PID_FILE`
	## check process
	while (true); do
        	/usr/local/nagios/libexec/check_tcp -H $HOST -p $PORT
	        if [ $? -eq 0 ];then
        	        break
        	fi
	        echo  "Waiting process $SERVICE start ..."
        	sleep 1
	done
	echo;echo "Started.";echo

}
func_stop() {
	if ! [ -f $PID_FILE ]; then
		echo "Process $SERVICE not started"
		return 0
	fi 
	PID=`cat $PID_FILE` 
	if ! [ $PID ]; then
               echo "Process $SERVICE not started"
               return 0
	fi 
	echo "Stoping service $SERVICE ..........."
	$CMD_STOP
	## check listen port
	while (true); do
        	/usr/local/nagios/libexec/check_tcp -H $HOST -p $PORT
		if [ $? -eq 2 ];then
			break
		fi
		echo  "Waiting process $SERVICE stop ..."
		sleep 1 
	done
	## check process
	while (true); do
		ps -ef|grep -v grep|grep -w $PID
		if [ $? -eq 1 ];then
        	        break
	        fi
	        echo "Waiting process $SERVICE stop ..."
	        sleep 1
	done

	echo;echo "Stoped.";echo
}

##Main
if [ "$PORT" == "_port" ]; then
	echo "pls update running port.."
	exit 1
fi

##restart
func_stop
func_start

