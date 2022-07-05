import React from 'react';
import './App.css';
import {useUserInfoContext} from "./context/UserInfoContext";
import BusStations from "./page/BusStations";
import BusSelector from "./page/BusSelector";


function App() {
    const {selectedBusInfo} = useUserInfoContext();

    return (
        <React.StrictMode>
            {selectedBusInfo ? <BusStations/> : <BusSelector/>}
        </React.StrictMode>
    );
}

export default App;
