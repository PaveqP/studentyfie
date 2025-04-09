import {FC, useState} from "react"
import {SideBar} from "../sideBar/SideBar"
import {Layout} from "antd"
import s from "./PageWrapper.module.scss"

const {Header, Content, Footer, Sider} = Layout

interface IPageWrapperProps {
    header?: React.ReactNode
    children?: React.ReactNode
    footer?: React.ReactNode
}

const PageWrapper: FC<IPageWrapperProps> = ({header, children, footer}) => {
    const [collapsed, setCollapsed] = useState<boolean>(true);

    return (
        <Layout className={s.layout}>
            <Sider collapsed={collapsed} width={collapsed ? 20 : 120}>
                <SideBar collapsed={collapsed} setCollapsed = {setCollapsed}/>
            </Sider>
            <Layout>
                {header && <Header className={s.header}>{header}</Header>}
                {children && <Content>{children}</Content>}
                {footer && <Footer>{footer}</Footer>}
            </Layout>
        </Layout>
    )
}

export {PageWrapper}
