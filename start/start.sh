rm -rf ./ytbus-*.log
nohup java -jar ./YT_BUS_Display-1.0.jar >ytbus-java.log 2>&1 &
nohup serve -s ../yantai_bus_display_react/build -l 8811 >ytbus-react.log 2>&1 &
