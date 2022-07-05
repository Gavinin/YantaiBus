import React from "react";
import {useUserInfoContext} from "../context/UserInfoContext";
import useMount from "../hooks/UseMount";
import http from "../util/RequestUtil";
import YT_BUS_APIS from "../common/BusApiStatus";
import HTTP_METHODS from "../common/HttpMethod";
import {Fragment, useState} from "react";
import styled from "@emotion/styled";
import {Menu} from "antd";
import {ItemType} from "antd/lib/menu/hooks/useItems";
import GetOnlineBusData from "../entity/GetOnlineBusData";
import DIRECTION from "../common/Directions";


interface LineList {
    line: string,
    detail: string
}

const BusSelector = () => {
    const {setInfo} = useUserInfoContext();
    const [lines, setLines] = useState<LineList[] | null>(null);

    const onBtn = (key: any) => {
        let userSelected: GetOnlineBusData = {
            linename: key.toString(),
            upordown: DIRECTION.UPWARD
        }
        setInfo(userSelected)
    }

    const LineDisplay = () => {

        let item: ItemType[] = []
        lines?.forEach((value) => {
            item.push({
                label: `${value.line}(${value.detail})`,
                key: value.line
            })
        })

        return (
            <Menu
                onClick={(key) => {
                    onBtn(key)
                }}
                items={item}
                mode={"vertical"}
            >
            </Menu>
        )
    }
    const getMapInfo = async () => {
        await http(YT_BUS_APIS.LINES, {
            method: HTTP_METHODS.GET,
            data: {}
        }).then((response) => {
            if (response.data.length > 0) {
                let result: Array<LineList> = JSON.parse(response.data.json())
                setLines(result);
            }
        })
    }

    //get line list
    useMount(async () => {
        await getMapInfo();
    })

    return (
        <Fragment>
            {lines ? <LineDisplay/> : null}
        </Fragment>
    )
}

export default BusSelector;


const LineStyle = styled.li`
  list-style: none;
`

const LinesStyle = styled.ul`
  display: none;
`
