import UserDataStats from "../common/UserDataStats";
import GetOnlineBusData from "../entity/GetOnlineBusData";

export const getItem = (key: string) => localStorage.getItem(key);
export const setItem = (key: string, value: string) => localStorage.setItem(key, value);
export const removeItem = (key: string) => localStorage.removeItem(key);

export const getUserInformation = () => {
    let busLine = getItem(UserDataStats.SELECTED_BUS_LINE);
    let busDirec = getItem(UserDataStats.SELECTED_BUS_DIRECTION);
    if (busLine !== null && busDirec !== null) {
        let userInfo: GetOnlineBusData = {
            linename: decodeURI(busLine),
            upordown: decodeURI(busDirec)
        }
        return userInfo;
    }
    return null;
}
export const cleanUserInformation = () => {
    removeItem(UserDataStats.SELECTED_BUS_LINE);
    removeItem(UserDataStats.SELECTED_BUS_DIRECTION);

}

export const setUserInformation = (userInfo: GetOnlineBusData) => {
    if (userInfo.linename !== undefined && userInfo.upordown !== undefined) {
        setItem(UserDataStats.SELECTED_BUS_LINE, userInfo.linename);
        setItem(UserDataStats.SELECTED_BUS_DIRECTION, userInfo.upordown);
    }
}


