
import React from "react";
import ReactDOM from "react-dom";
import "antd/dist/antd.css";
import styles from './styles/style.less';
import { Menu, Icon, Button, PageHeader, Dropdown } from "antd";


const menu = (
  <Menu>
  <Menu.Item>
  <a target="_blank" rel="noopener noreferrer" href="/user/search">
  Search
  </a>
  </Menu.Item>
  <Menu.Item>
  <a target="_blank" rel="noopener noreferrer" href="/user/restaurants">
  Restaurants
  </a>
  </Menu.Item>
  <Menu.Item>
  <a target="_blank" rel="noopener noreferrer" href="">
  Order
  </a>
  </Menu.Item>
  <Menu.Item>
  <a target="_blank" rel="noopener noreferrer" href="/user/update">
  Profile
  </a>
  </Menu.Item>
  <Menu.Item>
  <a target="_blank" rel="noopener noreferrer" href="">
  Logout
  </a>
  </Menu.Item>
  </Menu>
);
class App extends React.Component {
  state = {
    collapsed: false
  };

  toggleCollapsed = () => {
  this.setState({
                  collapsed: !this.state.collapsed
});
};

render() {
  return (

    <div >
    <PageHeader
  style={{
    border: '1px solid rgb(235, 237, 240)',
  }}

  title="Welcome!"

  subTitle="   Let's order something yummy!"
    />

    <Dropdown overlay={menu}>
    <a className="ant-dropdown-link" href="#">
    Menu <Icon type="down" />
    </a>
    </Dropdown>
    </div>
);
}
}

export default App;
