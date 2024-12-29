<template>
  <div>
    <div class="login-container"
         style="min-height: 100vh;overflow:hidden;display: flex;align-items: center;justify-content: center">
      <div style="width: 350px" class="login-box">
        <div style="font-weight: bold;font-size:24px;text-align: center;margin-bottom: 30px">注册</div>
        <el-form :model="data.form" style="padding-right: 30px" ref="formRef" :rules="rules" label-width="100px"
                 label-position="right">
          <el-form-item label="工人工号" prop="no">
            <el-input v-model="data.form.no" autocomplete="off" />
          </el-form-item>

          <el-form-item label="工人账号" prop="username">
            <el-input v-model="data.form.username" autocomplete="off" />
          </el-form-item>

          <el-form-item label="工人密码" prop="password">
            <el-input show-password v-model="data.form.password" autocomplete="off" :disabled="data.form.id!==undefined"/>
          </el-form-item>
          <el-form-item label="工人姓名">
            <el-input v-model="data.form.name" autocomplete="off"/>
          </el-form-item>
          <el-form-item prop="workshop" label="工人车间">
            <el-select style="width:100%" v-model="data.form.workshop">
              <el-option value="蜡模车间" label="蜡模车间"/>
              <el-option value="制壳车间" label="制壳车间"/>
              <el-option value="融化车间" label="融化车间"/>
              <el-option value="后处理车间" label="后处理车间"/>
              <el-option value="机加工车间" label="机加工车间"/>
              <el-option value="材料库" label="材料库"/>
              <el-option value="设备管理组" label="设备管理组"/>
              <el-option value="设备研发组" label="设备研发组"/>
              <el-option value="工装模具研发组" label="工装模具研发组"/>
              <el-option value="成品库" label="成品库"/>
              <el-option value="铸锻件外协采购和工序外协" label="铸锻件外协采购和工序外协"/>
            </el-select>
          </el-form-item>
          <el-form-item prop="teamsGroups" label="工人班组">
            <el-select style="width:100%" v-model="data.form.teamsGroups">
              <el-option value="模具库组" label="模具库组" v-if="data.form.workshop==='蜡模车间'"/>
              <el-option value="陶瓷芯班芯组" label="陶瓷芯班芯组" v-if="data.form.workshop==='蜡模车间'"/>
              <el-option value="射蜡组" label="射蜡组" v-if="data.form.workshop==='蜡模车间'"/>
              <el-option value="修蜡班" label="修蜡班" v-if="data.form.workshop==='蜡模车间'"/>
              <el-option value="组树班" label="组树班" v-if="data.form.workshop==='蜡模车间'"/>
              <el-option value="制壳班组" label="制壳班组" v-if="data.form.workshop==='制壳车间'"/>
              <el-option value="熔化班组班组" label="熔化班组班组" v-if="data.form.workshop==='融化车间'"/>
              <el-option value="切割班组班组" label="切割班组班组" v-if="data.form.workshop==='融化车间'"/>
              <el-option value="精磨班组" label="精磨班组" v-if="data.form.workshop==='融化车间'"/>
              <el-option value="抛丸班组" label="抛丸班组" v-if="data.form.workshop==='后处理车间'"/>
              <el-option value="调型班组" label="调型班组" v-if="data.form.workshop==='后处理车间'"/>
              <el-option value="手砂轮班组" label="手砂轮班组" v-if="data.form.workshop==='后处理车间'"/>
              <el-option value="工序外协组" label="工序外协组" v-if="data.form.workshop==='后处理车间'"/>
              <el-option value="数控班组" label="数控班组" v-if="data.form.workshop==='机加工车间'"/>
              <el-option value="钻床班组" label="钻床班组" v-if="data.form.workshop==='机加工车间'"/>
              <el-option value="清洗班组" label="清洗班组" v-if="data.form.workshop==='机加工车间'"/>
              <el-option value="材料库组" label="材料库组" v-if="data.form.workshop==='材料库'"/>
              <el-option value="设备管理组" label="设备管理组" v-if="data.form.workshop==='设备管理组'"/>
              <el-option value="设备研发组" label="设备研发组" v-if="data.form.workshop==='设备研发组'"/>
              <el-option value="工装摸具组" label="工装摸具组" v-if="data.form.workshop==='工装模具研发组'"/>
              <el-option value="成品库组" label="成品库组" v-if="data.form.workshop==='成品库'"/>
              <el-option value="铸锻件外协采购和工序外协组" label="铸锻件外协采购和工序外协组"
                         v-if="data.form.workshop==='铸锻件外协采购和工序外协'"/>
            </el-select>
          </el-form-item>
          <el-form-item label="工人性别">
            <el-radio-group v-model="data.form.sex">
              <el-radio label="男"></el-radio>
              <el-radio label="女"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="入职时间">
            <el-date-picker format="YYYY-MM-DD" value-format="YYYY-MM-DD" v-model="data.form.seniority"/>
          </el-form-item>
          <el-form-item label="工人工级">
            <el-input-number v-model="data.form.grade" :min="1" :max="10"/>
          </el-form-item>

          <el-form-item label="工人头像">
            <el-upload action="http://localhost:9090/uploadFile" list-type="picture" v-model="data.form.avatar"
                       :limit="1"
                       :on-success="handleImgUploadSuccess">
              <el-button type="primary">点击上传</el-button>
            </el-upload>
          </el-form-item>

          <el-form-item label="历史记录">
            <el-input v-model="data.form.history" autocomplete="off"/>
          </el-form-item>
          <el-form-item prop="role" label="工人职位">
            <el-select style="width:100%" v-model="data.form.role">
              <el-option value="车间主任" label="车间主任"></el-option>
              <el-option value="车间班组长" label="车间班组长"></el-option>
              <el-option value="工人" label="工人"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 100%" @click="register">注册</el-button>
          </el-form-item>
        </el-form>
        <div style="margin-top: 30px;text-align: right">
          已有账号？请<a href="/login">登录</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import request from "@/utils/request";
import { ElMessage } from "element-plus";
import router from "@/router";

const data = reactive({
  form: {
    avatar: ''
  }
});

const rules = reactive({
  username: [
    { required: true, message: "请输入账号", trigger: "blur" },
  ],
  password: [
    { required: true, message: "请输入密码", trigger: "blur" },
  ]
});

const formRef = ref();

const register = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      request.post('/register', data.form).then(res => {
        if (res.code === '200') {
          console.log(res);
          localStorage.setItem("users", JSON.stringify(data.form.username));
          ElMessage.success('注册成功,您的帐号为' + data.form.username);
          setTimeout(() => {
            router.push("/login");
          }, 3000);
        } else {
          ElMessage.error(res.msg);
          console.log(res);
        }
      });
    }
  });
};

const handleImgUploadSuccess = (response, file) => {
  if (response.code === '200' && response.data) {
    data.form.avatar = response.data; // 使用后端返回的图片URL更新avatar字段
    ElMessage.success('头像上传成功');
  } else {
    ElMessage.error('头像上传失败，请重试');
  }
};

</script>

<style scoped>
.login-container {
  min-height: 100vh;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  background: url("https://img0.baidu.com/it/u=4234357226,4114415663&fm=253&fmt=auto&app=138&f=PNG?w=900&h=462&") no-repeat;
  background-size: cover;
}

.login-box {
  background-color: rgba(255, 255, 255, 0.3);
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 30px;
  border-radius: 5px;
}
</style>