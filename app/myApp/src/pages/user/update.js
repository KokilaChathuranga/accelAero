import React from "react";
import ReactDOM from "react-dom";
import "antd/dist/antd.css";
import styles from './styles/style.less';
//import "./index.css";
import {
  Form,
  Input,
  Tooltip,
  Icon,
  Cascader,
  Select,
  Row,
  Col,
  Checkbox,
  Button,
  AutoComplete
} from "antd";

const { Option } = Select;
const AutoCompleteOption = AutoComplete.Option;
const baseUrl = 'http://localhost:9999/';

class RegistrationForm extends React.Component {
  state = {
    confirmDirty: false,
    autoCompleteResult: []
  };

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
    //headers.set('Authorization', 'Basic ' + base64.encode(username + ":" + password));
const options = {
      method: 'POST',
      headers: {'Content-Type':'application/json',
        'Accept': 'application/json'},
      //headers: JSON.stringify(values,url),
      body : JSON.stringify({"firstName" : values.firstname, "lastName" : values.lastname, "mobileNo" : values.phone, "email" : values.email})
    };
    const request = new Request(baseUrl+'customer/update', options);
  
    return fetch(request, options).then(response => response.json());
  
  }

  handleConfirmBlur = e => {
    const { value } = e.target;
    this.setState({ confirmDirty: this.state.confirmDirty || !!value });
  };

  compareToFirstPassword = (rule, value, callback) => {
    const { form } = this.props;
    if (value && value !== form.getFieldValue("password")) {
      callback("Two passwords that you enter is inconsistent!");
    } else {
      callback();
    }
  };

  validateToNextPassword = (rule, value, callback) => {
    const { form } = this.props;
    if (value && this.state.confirmDirty) {
      form.validateFields(["confirm"], { force: true });
    }
    callback();
  };

  handleWebsiteChange = value => {
    let autoCompleteResult;
    if (!value) {
      autoCompleteResult = [];
    } else {
      autoCompleteResult = [".com", ".org", ".net"].map(
        domain => `${value}${domain}`
      );
    }
    this.setState({ autoCompleteResult });
  };

  render() {
    const { getFieldDecorator } = this.props.form;
    const { autoCompleteResult } = this.state;

    const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      }
    };
    const tailFormItemLayout = {
      wrapperCol: {
        xs: {
          span: 24,
          offset: 0
        },
        sm: {
          span: 16,
          offset: 8
        }
      }
    };
    const prefixSelector = getFieldDecorator("prefix", {
      initialValue: "94"
    })(
      <Select style={{ width: 70 }}>
        <Option value="94">+94</Option>
        <Option value="91">+91</Option>
      </Select>
    );

    return (
      <Form {...formItemLayout} onSubmit={this.handleSubmit} className={styles.registerform}>
        <Form.Item {...tailFormItemLayout}>       
          <Button type="primary" htmlType="submit" className={styles.loginformbutton}>
            Edit Profile
          </Button>
        </Form.Item>
        <Form.Item label="First Name">
          {getFieldDecorator("firstname", {
            rules: [
              {
                required: true,
                message: "Please input your first name!",
                whitespace: true
              }
            ]
          })(<Input />)}
        </Form.Item>
        <Form.Item label="Last Name">
          {getFieldDecorator("lastname", {
            rules: [
              {
                required: true,
                message: "Please input your last name!",
                whitespace: true
              }
            ]
          })(<Input />)}
        </Form.Item>
        <Form.Item label="E-mail">
          {getFieldDecorator("email", {
            rules: [
              {
                type: "email",
                message: "The input is not valid E-mail!"
              },
              {
                required: true,
                message: "Please input your E-mail!"
              }
            ]
          })(<Input />)}
        </Form.Item>        
        <Form.Item label="Mobile No">
          {getFieldDecorator("phone", {
            rules: [
              { required: true, message: "Please input your Mobile number!" }
            ]
          })(<Input addonBefore={prefixSelector} style={{ width: "100%" }} />)}
        </Form.Item>
        
        <Form.Item {...tailFormItemLayout}>       
          <Button type="primary" htmlType="submit" className={styles.loginformbutton}>
            Submit
          </Button>
        </Form.Item>
      </Form>
    );
  }
}

const WrappedRegistrationForm = Form.create({ name: "register" })(
  RegistrationForm
);

export default WrappedRegistrationForm;
