<template>
  <div>
    <div class="login-container"
         style="min-height: 100vh;overflow:hidden;display: flex;align-items: center;justify-content: center">
      <div style="width: 350px" class="login-box">
        <div style="font-weight: bold;font-size:24px;text-align: center;margin-bottom: 30px">登录</div>
        <el-form :model="data.form" ref="formRef" :rules="rules">
          <el-form-item prop="username">
            <el-input style="width: 100%" prefix-icon="User" v-model="data.form.username"
                      placeholder="请输入账号"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input show-password style="width: 100%" prefix-icon="Lock" v-model="data.form.password"
                      placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item prop="role">
            <el-select style="width:100%"  v-model="data.form.role">
              <el-option value="管理员" label="管理员"></el-option>
              <el-option value="车间主任" label="车间主任"></el-option>
              <el-option value="车间班组长" label="车间班组长"></el-option>
              <el-option value="工人" label="工人"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 100%" @click="login">登录</el-button>
          </el-form-item>
        </el-form>
        <div style="margin-top: 30px;text-align: right">
          还没有账号？请<a href="/register">注册</a>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup>
import {reactive, ref} from "vue"
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import router from "@/router";

const data = reactive({
  form: {}
})

const rules = reactive({
  username: [
    {required: true, message: "请输入账号", trigger: "blur"},
  ],
  password: [
    {required: true, message: "请输入密码", trigger: "blur"},
  ]
})
const formRef = ref()
const login = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      request.post('/login', data.form).then(res => {
        console.log("张润泽");
        if (res.data['code'] === '200') {
          localStorage.setItem("users", JSON.stringify(res.data.data));

          ElMessage.success('登录成功');
          //location.href="/home"; //跳转到主页
          router.push("/home")
        } else {
          ElMessage.error(res.data['msg']);
          console.log(res)
        }
      })
    }
  })
}
</script>
<style scoped>
.login-container {
  min-height: 100vh;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  background: url("https://images.pexels.com/photos/290595/pexels-photo-290595.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1") no-repeat;
  background-size: cover;
}

.login-box {
  background-color: rgba(255, 255, 255, 0.3);
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 30px;
  border-radius: 5px;
}
</style>
