import {FC} from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {MainPage} from "../pages";
import {ERoutes} from "./routes";

const AppRouter: FC = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path={ERoutes.Main} element={<MainPage />}></Route>
            </Routes>
        </BrowserRouter>
    )
}

export default AppRouter;