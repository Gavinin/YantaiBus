import React, {useEffect} from "react";
import 'antd/dist/antd.css';
import {LoadingOutlined} from '@ant-design/icons';
import {useUserInfoContext} from "../context/UserInfoContext";
import useMount from "../hooks/UseMount";
import http from "../util/RequestUtil";
import YT_BUS_APIS from "../common/BusApiStatus";
import HTTP_METHODS from "../common/HttpMethod";
import {Fragment, useState} from "react";
import styled from "@emotion/styled";
import {Button, Divider, Menu, Spin} from "antd";
import {ItemType} from "antd/lib/menu/hooks/useItems";
import GetOnlineBusData from "../entity/GetOnlineBusData";
import DIRECTION from "../common/DirectionsEnum";
import Search from "antd/es/input/Search";


interface LineList {
    line: string,
    detail: string
}

interface BusInfo {
    label: string,
    key: string
}

const BusSelector = () => {
    const {selectedBusInfo, setInfo} = useUserInfoContext();
    const [linesDisplay, setLinesDisplay] = useState<ItemType[]>([]);
    const [searchLines, setSearchLines] = useState<string | null>(null);
    const [direction, setDirection] = useState<string>(DIRECTION.UPWARD);

    useEffect(() => {
        getMapInfo();
    }, [searchLines, direction])

    //get line list
    useMount(async () => {
        await getMapInfo();
    })

    const getNum = (str: string) => {
        return parseInt(str)
    }

    const containNum = (str: string) => {
        return !isNaN(getNum(str))
    }

    const onBtn = (data: any) => {
        let userSelected: GetOnlineBusData = {
            linename: data.key,
            upordown: direction
        }
        setInfo(userSelected)
    }

    const chDirection = () => {
        if (direction === DIRECTION.UPWARD) {
            setDirection(DIRECTION.DOWNWARD)
        } else {
            setDirection(DIRECTION.UPWARD)
        }
        if (selectedBusInfo) {
            let userSelected: GetOnlineBusData = {
                linename: selectedBusInfo.linename,
                upordown: direction
            }
            setInfo(userSelected)
        }


    }

    const LineDisplay = () => {
        return (
            <Fragment>
                {/*<Divider/>*/}
                <SearchStyle
                    placeholder="输入线路"
                    allowClear
                    enterButton="Search"
                    size="middle"

                    onSearch={(data) => {
                        // if (data !== null && data !== "") {
                        setSearchLines(data);
                        // }
                    }}
                />
                <br/>
                <DirectionButtonStyle onClick={chDirection} type="primary" block>{direction}</DirectionButtonStyle>

                <Menu
                    onClick={(key) => {
                        onBtn(key)
                    }}
                    items={linesDisplay}
                    mode={"inline"}
                />

            </Fragment>
        )
    }
    const getMapInfo = async () => {
        let sendData: {
            line: string,
            direction: string
        } = {
            line: "",
            direction: direction
        };
        if (searchLines != null) {
            sendData.line = searchLines
        }
        await http(YT_BUS_APIS.LINES, {
            method: HTTP_METHODS.GET,
            data: sendData
        }).then((response) => {
            let result: Array<LineList> = response.data
            let item: BusInfo[] = []
            result?.forEach((value) => {
                item.push({
                    label: `${value.line}(${value.detail})`,
                    key: value.line
                })
            })
            item.sort((a: BusInfo, b: BusInfo) => {

                if (containNum(a.key) && !containNum(b.key)) {
                    return -1
                } else if (!containNum(a.key) && containNum(b.key)) {
                    return 1
                }
                if (containNum(a.key) && containNum(b.key)) {
                    if (getNum(a.key) >= getNum(b.key)) {
                        return 1
                    } else {
                        return -1
                    }
                }
                return 0


            })

            setLinesDisplay(item);
        })
    }

    return (
        <Fragment>
            {linesDisplay ? <LineDisplay/> : <NoneDataStyle/>}
        </Fragment>
    )
}

export default BusSelector;

const NoneData = () => {
    const antIcon = <LoadingOutlined style={{fontSize: 24}} spin/>;
    return (
        <Spin size={"large"} indicator={antIcon}/>
    )
}

const NoneDataStyle = styled(NoneData)`
  justify-content: center;
  align-items: center;
`

const SearchStyle = styled(Search)`
  //box-sizing: border-box;
  //border: 1px solid #eae4a1 !important;
  //padding: 2px;
`

const DirectionButtonStyle = styled(Button)`
  box-sizing: border-box;
  border: 2px solid #eae4a1 !important;
  //padding: 4px;
`
