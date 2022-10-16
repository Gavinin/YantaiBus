import React from "react";

import {useEffect, useRef} from 'react';

const useInterval = (callback: Function, delay: number = 1000) => {
    const savedCallback = useRef<Function>(() => {
    });

    useEffect(() => {
        savedCallback.current = callback;
    });

    useEffect(() => {
        if (delay !== null) {
            const interval = setInterval(() => savedCallback.current(), delay);
            return () => clearInterval(interval);
        }

        return undefined;
    }, [delay]);
};

export default useInterval