import {FC} from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {Main, Profile} from "../pages";
import {ERoutes} from "./routes";

const AppRouter: FC = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path={ERoutes.Main} element={<Main />}></Route>
                <Route path={ERoutes.Profile} element={<Profile />}></Route>
            </Routes>
        </BrowserRouter>
    )
}

export default AppRouter;