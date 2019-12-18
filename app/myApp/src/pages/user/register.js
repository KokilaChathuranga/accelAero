import React from "react";
import ReactDOM from "react-dom";
import "antd/dist/antd.css";
import styles from './styles/style.less';
import { Form,
  Input,
  Tooltip,
  Icon,
  Cascader,
  Select,
  Row,
  Col,
  Checkbox,
  Button,
  AutoComplete} from "antd";
import router from 'umi/router';


const baseUrl = 'http://localhost:9999/';
const { Option } = Select;
const AutoCompleteOption = AutoComplete.Option;

class RegistrationForm extends React.Component {
  state = {
    confirmDirty: false,
    autoCompleteResult: []
  };

  // constructor(props) {
  //   super(props);
  //     router.push(`/welcome`);
  // }

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
  const headers = new Headers();
  headers.append('Content-type', 'application/json');
  const options = {
    method: 'POST',
    headers: {'Content-Type':'application/json',
      'Accept': 'application/json'},
    body : JSON.stringify({"username" : values.username, "password" : values.password, "passwordConfirm" : values.passwordConfirm})
  };
  const request = new Request(baseUrl + 'registration', options);

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
<Form.Item label="User Name">
    {getFieldDecorator("username", {
    rules: [
      {
        required: true,
        message: "Please input your user name!",
        whitespace: true
      }
    ]
  })(<Input />)}
</Form.Item>

  <Form.Item label="Password" hasFeedback>
  {getFieldDecorator("password", {
    rules: [
      {
        required: true,
        message: "Please input your password!"
      },
      {
        validator: this.validateToNextPassword
      }
    ]
  })(<Input.Password />)}
</Form.Item>
  <Form.Item label="Confirm Password" hasFeedback>
  {getFieldDecorator("confirm", {
    rules: [
      {
        required: true,
        message: "Please confirm your password!"
      },
      {
        validator: this.compareToFirstPassword
      }
    ]
  })(<Input.Password onBlur={this.handleConfirmBlur} />)}
  </Form.Item>

  <Form.Item {...tailFormItemLayout}>
  {getFieldDecorator("agreement", {
    valuePropName: "checked"
  })(
  <Checkbox>
  <a href="">I have read the agreement</a>
  </Checkbox>
  )}
</Form.Item>
  <Form.Item {...tailFormItemLayout}>
<Button type="primary" htmlType="submit" href="" className={styles.loginformbutton}>
  Register
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
