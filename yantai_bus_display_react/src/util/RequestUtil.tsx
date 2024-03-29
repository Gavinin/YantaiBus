import qs from "qs";
import React from "react";
import AppResult from "../entity/AppResult";

interface IConfig extends RequestInit {
    data?: any;
}

export enum BUS_STATUS {
    SUCCESS = 1
}

export enum BUS_INFO_STATUS {
    SUCCESS = "Success"
}


const http = async (
    url: string,
    {data, headers, ...customConfig}: IConfig = {}
) => {
    const config = {
        method: "GET",
        headers: {
            "Content-Type": data ? "application/json" : "",
        },
        ...customConfig,
    };

    if (config.method.toUpperCase() === "GET") {
        url = `${url}?${qs.stringify(data)}`;
    } else {
        config.body = JSON.stringify(data || {});
    }
    return window.fetch(url,config).then(async (res) => {
        let dataRes: AppResult;
        dataRes = await res.json();
        if (dataRes.status !== BUS_STATUS.SUCCESS || dataRes.info !== BUS_INFO_STATUS.SUCCESS) {
            return Promise.reject({message: "请求失败"});
        }
        if (dataRes.info) {
            return dataRes;
        } else {
            return Promise.reject(dataRes);
        }
    });
}

export default http