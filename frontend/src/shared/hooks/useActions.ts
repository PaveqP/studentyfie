import {bindActionCreators} from "@reduxjs/toolkit"
import {useMemo} from "react"
import {useDispatch} from "react-redux"
import {utilActions} from "../../store"

export const useActions = () => {
    const rootActions = {
        ...utilActions,
    };

    const dispatch = useDispatch()

    return useMemo(() =>  bindActionCreators(rootActions, dispatch), [dispatch])
}