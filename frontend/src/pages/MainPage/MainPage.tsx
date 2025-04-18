import {FC} from "react"
import {PageWrapper, MainHeader, ExchangeProgramList} from "../../components"

const Main: FC = () => {
    return (
        <PageWrapper header={<MainHeader />}>
            <ExchangeProgramList />
        </PageWrapper>
    )
}

export {Main}
