import {Button, Flex} from "antd";
import {MenuOutlined, HomeOutlined, LoginOutlined, LogoutOutlined, UserOutlined, SunFilled, MoonFilled} from "@ant-design/icons"
import {FC} from "react"
import s from "./SideBar.module.scss"
import {useNavigate} from "react-router-dom"
import {ERoutes} from "../../app"
import {useTheme, useTypedSelector} from "../../shared"
import cn from "classnames"
import {useGetStudentsQuery} from "../../api";

interface ISideBarProps {
    collapsed: boolean;
    setCollapsed: (collapsed: boolean) => void;
}

const SideBar: FC<ISideBarProps> = ({collapsed, setCollapsed}) => {
    const navigate = useNavigate();
    const {toggleTheme} = useTheme();
    const isDark = useTypedSelector(state => state.util.isDark);
    const {data: students, isLoading, isError} = useGetStudentsQuery();
    console.log(isLoading, isError, students)

    return (
        <Flex vertical align="center" justify="space-between" className={cn(s.container, {
            [s.containerDark]: isDark
        })}>
            <Flex vertical align="center" gap={20} className={s.flex}>
                <Button
                    onClick={() => setCollapsed(!collapsed)}
                    icon={<MenuOutlined />}
                    className={s.button}
                />
                <Button
                    onClick={() => navigate(ERoutes.Main)}
                    icon={<HomeOutlined />}
                    className={s.button}
                >
                    {!collapsed && <>Главная</>}
                </Button>
                <Button
                    onClick={toggleTheme}
                    icon={isDark ? <MoonFilled /> : <SunFilled />}
                    className={s.button}
                >
                    {!collapsed && <>{isDark ? "Темная" : "Светлая"}</>}
                </Button>
            </Flex>
            <Flex vertical align="center" gap={20} className={s.flex}>
                <Button icon={<UserOutlined />} className={s.button}>
                {!collapsed && <>Профиль</>}
            </Button>
                <Button icon={<LoginOutlined />} className={s.button}>
                {!collapsed && <>Войти</>}
            </Button>
                <Button icon={<LogoutOutlined />} className={s.button}>
                {!collapsed && <>Выйти</>}
            </Button>
            </Flex>
        </Flex>
    )
}

export {SideBar}
