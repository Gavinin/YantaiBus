import React, {useEffect, useState} from "react";
import styled from "@emotion/styled";
import useInterval from "../hooks/userInterval";
import GetOnlineBusResult from "../entity/GetOnlineBusResult";
import http from "../util/RequestUtil";
import YT_BUS_APIS from "../common/BusApiStatus";
import {ReactComponent as BusLogo} from "../common/svg/bus.svg"
import {useUserInfoContext} from "../context/UserInfoContext";
import MapInfo from "../entity/MapInfo";
import HTTP_METHODS from "../common/HttpMethod";
import busData from "../common/json/test.json"
import {Button, Card, Timeline} from "antd";
import useMount from "../hooks/UseMount";


const BusStations = () => {
    const {selectedBusInfo,setInfo, cleanInfo} = useUserInfoContext()
    const [busInfoArray, setBusInfoArray] = useState<Array<GetOnlineBusResult> | null>(null)
    const [mapInfoArray, setMapInfoArray] = useState<MapInfo[] | null>(null)
    const [directionBtn, setDirectionBtn] = useState<boolean>(false)

    const getBusInfo = async () => {
        await http(YT_BUS_APIS.BUS, {
            data: selectedBusInfo
        }).then((response) => {
            if (response.data.length > 0) {
                let result: Array<GetOnlineBusResult> = response.data
                setBusInfoArray(result);
            } else {
                setBusInfoArray([]);
            }
        })
    }

    const getMapInfo = async () => {
        await http(YT_BUS_APIS.MAP, {
            method: HTTP_METHODS.POST,
            data: selectedBusInfo
        }).then((response) => {
            if (response.data.length > 0) {
                let result: Array<MapInfo> = response.data
                setMapInfoArray(result);
            }
        })
    }
    useMount(()=>{
        getMapInfo();
        getBusInfo();
    })

    useEffect(() => {
        getMapInfo();
        getBusInfo();

    },[selectedBusInfo])
    useInterval(async () => {

        await getBusInfo();
    }, 3000)

    const Main = () => {
        let timeLineItems: Array<JSX.Element> = []
        let currentBusArray = busInfoArray;

        //寻找当前车辆和地图的相对位置
        currentBusArray?.forEach((currentBus) => {
            if(currentBus.inorder==="0"){
                let busTmp = <Timeline.Item key={currentBus.busno} dot={<BusLogo/>}>
                    {currentBus.linename}--{currentBus.busno}
                    <br/> 速度：{currentBus.speed}
                </Timeline.Item>
                timeLineItems.push(busTmp)
            }
        })
        mapInfoArray?.forEach((mapInfo) => {
                let tmp = <Timeline.Item key={mapInfo.stopOrder}
                                         label={mapInfo.stopOrder}>{mapInfo.stopName}{mapInfo.side}</Timeline.Item>
                timeLineItems.push(tmp)
                currentBusArray?.forEach((currentBus) => {
                    if (mapInfo.stopOrder === currentBus.inorder) {
                        let busTmp = <Timeline.Item key={currentBus.busno} dot={<BusLogo/>}>
                            {currentBus.linename}--{currentBus.busno}
                           <br/> 速度：{currentBus.speed}
                        </Timeline.Item>
                        timeLineItems.push(busTmp)
                    }

                })
            })


        return (

            <Timeline mode={'left'} >
                {
                    timeLineItems.map((value) => (
                        value
                    ))
                }
            </Timeline>


        )
    }


    return (
        <BasePage title={selectedBusInfo?.linename} bordered={false}>


            <Main/>
            <Button onClick={cleanInfo}>返回</Button>


        </BasePage>
    )
}

const StopStyle = styled.li`
  list-style: none;
  border: 1px solid #2e2e2e;
  border-radius: 2px;
  background: #2e2e2e;
`

const StopsStyle = styled.ul`
  display: none;
`

const BasePage = styled(Card)`
  //display: flex;
  //align-content: center;
  //justify-content: space-between;

`

const Header = styled.header`
  background-color: rgba(74, 178, 78, 0.97);

`

const Content = styled.div`

`

export default BusStations;