// Uncomment this line to use CSS modules
// import styles from './app.module.scss';
import NxWelcome from './nx-welcome';
import {SharedUi} from '@sberhack/shared-ui';

export function App() {
  SharedUi()
  return (
    <div>
      <NxWelcome title="sberhack" />
    </div>
  );
}

export default App;
