import React, {useState} from "react";
import GetOnlineBusData from "../entity/GetOnlineBusData";
import useMount from "../hooks/UseMount";
import {cleanUserInformation, getUserInformation, setUserInformation} from "../util/LocalStorageUtils";

interface IBusInfoContext {
    selectedBusInfo: GetOnlineBusData | null,
    setInfo: (userInfo: GetOnlineBusData) => boolean,
    cleanInfo: () => void
}

const UserInfoContext = React.createContext<IBusInfoContext | undefined>(undefined)
UserInfoContext.displayName = "UserInfoContext"


export const UserInfoProvider = ({children}: { children: React.ReactNode }) => {
    const [userInfo, _setUserInfo] = useState<GetOnlineBusData | null>(null)
    const setInfo = (userInfo: GetOnlineBusData) => {
        if (userInfo.linename !== null && userInfo.upordown !== null) {
            _setUserInfo(userInfo)
            setUserInformation(userInfo)
            return true
        }
        return false;
    }

    const cleanInfo = () => {
        _setUserInfo(null);
        cleanUserInformation();

    }


    useMount(() => {
        _setUserInfo(getUserInformation())
    })

    return <UserInfoContext.Provider children={children} value={{
        selectedBusInfo: userInfo,
        setInfo,
        cleanInfo
    }}/>
}


export const useUserInfoContext = () => {
    const context = React.useContext(UserInfoContext);
    if (!context) {
        throw new Error("useAuth MUST be running in UserInfoContext");
    }
    return context;
}