import React from 'react';
import ReactDOM from 'react-dom';
import 'antd/dist/antd.css';
import styles from './styles/style.less';
import { Input } from 'antd';
import { Button } from 'antd';

const { Search } = Input;

  class SearchForm extends React.Component {

    state = {
    };  
  
    render() {
      return ( 
        <div className={styles.searchform}>
        <Search placeholder="Search restaurants and food" onSearch={value => console.log(value)} enterButton />
        <br />
        <br />
        <br />
        <Button type="primary">Order</Button>
      </div>
      );
    }
  }
  
  export default SearchForm;
 