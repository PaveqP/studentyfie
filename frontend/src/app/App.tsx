import AppRouter from './AppRouter'
import {FC} from 'react'
import {App as AppProvider} from 'antd'
import {ConfigProvider} from 'antd';
import {useTypedSelector} from '../shared';

const App: FC = () => {
  const theme = useTypedSelector(state => state.util.theme);

  return (
    <ConfigProvider theme={theme}>
      <AppProvider>
        <AppRouter />
      </AppProvider>
    </ConfigProvider>
  )
}

export default App
