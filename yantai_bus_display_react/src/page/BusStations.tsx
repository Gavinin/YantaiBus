import React, {useEffect, useState} from "react";
import styled from "@emotion/styled";
import useInterval from "../hooks/userInterval";
import GetOnlineBusResult from "../entity/GetOnlineBusResult";
import http from "../util/RequestUtil";
import YT_BUS_APIS from "../common/BusApiStatus";

import {useUserInfoContext} from "../context/UserInfoContext";
import MapInfo from "../entity/MapInfo";
import HTTP_METHODS from "../common/HttpMethod";
import useMount from "../hooks/UseMount";


const BusStations = () => {
    const {selectedBusInfo, cleanInfo} = useUserInfoContext()
    const [busInfo, setBusInfo] = useState<Array<GetOnlineBusResult> | null>(null)
    const [mapInfo, setMapInfo] = useState<MapInfo[] | null>(null)
    const getBusInfo = async () => {
        await http(YT_BUS_APIS.BUS, {
            data: selectedBusInfo
        }).then((response) => {
            if (response.data.length > 0) {
                let result: Array<GetOnlineBusResult> = JSON.parse(response.data.json())
                setBusInfo(result);
            }
        })
    }

    const getMapInfo = async () => {
        await http(YT_BUS_APIS.MAP, {
            method: HTTP_METHODS.POST,
            data: selectedBusInfo
        }).then((response) => {
            if (response.data.length > 0) {
                let result: Array<MapInfo> = JSON.parse(response.data.json())
                setMapInfo(result);

            }
        })
    }
    useMount(async () => {
        await getMapInfo();
    })
    useInterval(async () => {

        await getBusInfo();
    }, 2000)

    const Main = () => {

        return (
            <StopsStyle>
                {
                    mapInfo?.map((item) => (
                        <StopStyle>
                            {`${item.stationNam}${item.side}`}
                        </StopStyle>
                    ))
                }


            </StopsStyle>
        )
    }


    return (
        <BasePage>
            <Header>
                <button type={"button"} onClick={cleanInfo}>返回</button>
                <h3>{selectedBusInfo?.linename}</h3>
            </Header>
            <Content>
                <Main/>
            </Content>

        </BasePage>
    )
}

const StopStyle = styled.li`
  list-style:none;
`

const StopsStyle = styled.ul`
  display: none;
`

const BasePage = styled.div`
  display: flex;
  align-content: center;
  justify-content: space-between;

`

const Header = styled.header`
  background-color: rgba(74, 178, 78, 0.97);

`

const Content = styled.div`

`

export default BusStations;