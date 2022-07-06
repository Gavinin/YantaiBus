import React, {useState} from 'react';
import 'antd/dist/antd.css';
import './App.css';
import {useUserInfoContext} from "./context/UserInfoContext";
import BusStations from "./page/BusStations";
import BusSelector from "./page/BusSelector";
import NoneBusStations from "./page/NoneBusStations";
import {BackTop, Layout} from 'antd';
import styled from "@emotion/styled";

const {Header, Content, Footer, Sider} = Layout;


const App: React.FC = () => {
    const {selectedBusInfo, setInfo} = useUserInfoContext();


    return (
        <React.StrictMode>


            <Layout style={{minHeight: '100vh'}}>
                <BackTop/>
                <SiderStyle
                    style={{
                        overflow: 'auto',
                        height: '100vh',
                        position: 'fixed',
                        left: 0,
                        top: 0,
                        bottom: 0,
                    }}
                    width={"30%"}>
                    <BusSelector/>
                </SiderStyle>
                {/*<Header className="site-layout-background" style={{padding: 0}}/>*/}
                <Layout className="site-layout" style={{marginLeft: '30%'}}>

                    <Content style={{margin: '16px'}}>
                        {selectedBusInfo ? <BusStations/> : <NoneBusStations/>}
                    </Content>
                </Layout>
            </Layout>

        </React.StrictMode>
    );
}


export default App;

const SiderStyle = styled(Sider)`
  overflow-x: hidden;

  &::-webkit-scrollbar {
    display: none;
  }
`
