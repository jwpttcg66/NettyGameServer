#!/bin/bash
export LANG=en_US.UTF8

start(){
        ulimit -n 65535
        #find the jars
        jar_lib=`ls -1 lib/*.jar`
        jar_lib=`echo $jar_lib | sed 's/ /:/g'`
        echo "path :$jar_lib"
        #init logs
        if [ ! -d 'logs' ] ; then mkdir logs ; fi
        java -Dserver.name=game-server -server -Xmx1024M -Xms1024M -Xmn512M -Xss256k -XX:NewRatio=2 -XX:PermSize=32m -XX:MaxPermSize=64m -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/logs  -XX:ErrorFile=/logs/hs_error%p.log -cp resource:resource:${jar_lib}  com.snowcattle.game.bootstrap.GameServer &
        pid=$!
        echo "$pid" >pid
        echo "game-server ProcessId:$pid"
        sleep 10
}

stop(){
                pid=`cat pid`
                #pid=`ps aux |grep java |awk '{if($11=="java") print $2}'`
                echo "Stop game-server Process Id:$pid"
                kill $pid
                rm -f pid
                sleep 5
}

case "$1" in
        start)
                stop
                start
                ;;
        stop)
                stop
                ;;
        *)
        echo $"Uasge: (start|stop)"
        exit;
esac
