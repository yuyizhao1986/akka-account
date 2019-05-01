#!/bin/bash
java -jar -Dakka.cluster.seed-nodes.0=akka.tcp://account@127.0.0.1:2552 -Dakka.remote.netty.tcp.hostname=127.0.0.1 -Dakka.remote.netty.tcp.port=0 -Dserver.port=9000 -Dlog.path=/home/logs/salve -Dapp.id=1  endpoint.jar
