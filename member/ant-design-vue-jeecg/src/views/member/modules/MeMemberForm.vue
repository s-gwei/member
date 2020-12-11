<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['name']" placeholder="请输入姓名"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="手机号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['phone']" placeholder="请输入手机号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="余额" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['balance']" placeholder="请输入余额" style="width: 100%" />
            </a-form-item>
          </a-col>
         <a-col :span="12">
         <a-form-item label="支付方式" >
                    <a-select  name="projectNameList" v-decorator="['consumtWay']" placeholder="请选择支付方式">
                        <a-select-option value="">请选择</a-select-option>
                        <a-select-option v-for="item in payWay" :key="item.name" :value="item.name"> {{item.name}}</a-select-option>
                    </a-select>
            </a-form-item>
         </a-col>
           <a-col :span="12">
            <a-form-item label="项目名称">
                    <a-select  name="projectNameList" v-decorator="['employeeId']" placeholder="请选择员工">
                        <a-select-option value="">请选择</a-select-option>
                        <a-select-option v-for="item in employee" :key="item.id" :value="item.id"> {{item.name}}</a-select-option>
                    </a-select>
            </a-form-item>
          </a-col> 
      
          <a-col v-if="showFlowSubmitButton" :span="24" style="text-align: center">
            <a-button @click="submitForm">提 交</a-button>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  export default {
    name: 'MeMemberForm',
    components: {
      JFormContainer,
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
        required: false
      },
      //表单模式：true流程表单 false普通表单
      formBpm: {
        type: Boolean,
        default: false,
        required: false
      },
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        form: this.$form.createForm(this),
        employee:{},
        payWay:[
          {name: "支付宝"},
          {name: "微信"},
          {name: "其他"}
        ],
        employeeId:'',
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/member/meMember/add",
          edit: "/member/meMember/edit",
          queryById: "/member/meMember/queryById",
          queryEmployInfo:"/member/meMployee/queryEmployInfo"
        }
      }
    },
    computed: {
      formDisabled(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return true
          }
        }
        return false
      }
    },
    created () {
      //如果是流程中表单，则需要加载流程表单data
      this.showFlowData();
      //获取员工信息
      this.queryEmployInfo();
    },
    methods: {
      queryEmployInfo(){
          let formData = {};
          let httpurl = this.url.queryEmployInfo;
          let   method = 'get';
          httpAction(httpurl,formData,method).then((res)=>{
                this.employee = res.result;
                console.info(this.employee)
            })
      },
      add () {
        console.info(this.selectId);
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'name','phone','balance',this.selectId))
        })
      },
      //渲染流程表单数据
      showFlowData(){
        if(this.formBpm === true){
          let params = {id:this.formData.dataId};
          getAction(this.url.queryById,params).then((res)=>{
            if(res.success){
              this.edit (res.result);
            }
          });
        }
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                console.info(res)
                that.$message.warning(res.message);
              }
            }).finally(() => {
              
                that.confirmLoading = false;
            })
          }
         
        })
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'name','phone','balance'))
      },
    }
  }
</script>