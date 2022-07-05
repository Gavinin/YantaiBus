export interface IStopInfo {
    stopName: string,
    stopOrder: string
}

export interface ILineInfo {
    stopArray: IStopInfo[]
}