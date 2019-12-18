import React from "react";
import ReactDOM from "react-dom";
import "antd/dist/antd.css";
import styles from './styles/style.less';
import { Form, Icon, Input, Button, Checkbox } from "antd";
import router from 'umi/router';
//import React, {PureComponent} from 'react';
//import {connect} from 'dva';
//import "./index.less";
const baseUrl = 'http://localhost:9999/';

// @connect(({sampleFlight, loading}) => ({
//     sampleFlight,
//     loading: loading.models.sampleFlight,
//   }))

class NormalLoginForm extends React.Component {
  handleSubmit = e => {
  e.preventDefault();
  this.props.form.validateFields((err, values) => {
  if (!err) {
  console.log("Received values of form: ", values);
  this.authenticate(values).then((res) => {
  console.log(res);
});
}
});
};

async authenticate(values) {
  //const newUrl = baseUrl + 'login?username=' + values.username+'&password='+values.password;
  const headers = new Headers();
  headers.append('Content-type', 'application/json');
  const options = {
    method: 'POST',
    headers: {'Content-Type':'application/json',
      'Accept': 'application/json'},
    //headers: JSON.stringify(values,url),
    body : JSON.stringify({"username" : values.username, "password" : values.password})
  };
  const request = new Request(baseUrl+'loginTest', options);

  return fetch(request, options).then(response => response.json());

}

render() {
  const { getFieldDecorator } = this.props.form;
  return (
    <Form onSubmit={this.handleSubmit} className="loginform" className={styles.loginform}>
<Form.Item>
  {getFieldDecorator("username", {
    rules: [{ required: true, message: "Please input your username!" }]
  })(
  <Input
  prefix={<Icon type="user" style={{ color: "rgba(0,0,0,.25)" }} />}
  placeholder="Username"
    />
)}
</Form.Item>
  <Form.Item>
  {getFieldDecorator("password", {
    rules: [{ required: true, message: "Please input your password!" }]
  })(
  <Input
  prefix={<Icon type="lock" style={{ color: "rgba(0,0,0,.25)" }} />}
  type="password"
  placeholder="Password"
    />
)}
</Form.Item>
  <Form.Item>
  {getFieldDecorator("remember", {
    valuePropName: "checked",
      initialValue: true
  })(<Checkbox>Remember me</Checkbox>)}

  <Button
  type="primary"
  htmlType="submit"
  className="loginformbutton"
  //href="/user/welcome"
  className={styles.loginformbutton}
>
  Login
  </Button>
  or <a href="/user/register">Register now!</a>
  </Form.Item>
  </Form>
);
}
}

const WrappedNormalLoginForm = Form.create({ name: "normal_login" })(
  NormalLoginForm
);

export default WrappedNormalLoginForm;
