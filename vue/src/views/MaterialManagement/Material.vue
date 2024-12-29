<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <el-input
          v-model="data.materialName"
          style="width: 260px;margin-right: 10px"
          placeholder="请输入材料名称查询"
          :prefix-icon="Search"
      />
      <el-input
          v-model="data.materialType"
          style="width: 260px;margin-right: 10px"
          placeholder="请输入材料类型查询"
          :prefix-icon="Search"
      />
      <el-button type="primary" style="margin-left:10px" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <el-button type="primary" @click="handleAdd">新增</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <el-table :data="data.tableData" style="width: 100%">
        <el-table-column prop="materialCode" label="材料编码"></el-table-column>
        <el-table-column prop="materialName" label="材料名称"></el-table-column>
        <el-table-column prop="materialType" label="材料类型"></el-table-column>
        <el-table-column prop="materialCategory" label="物料类型"></el-table-column>
        <el-table-column prop="quantity" label="数量"></el-table-column>
        <el-table-column prop="unitCost" label="单价"></el-table-column>
        <el-table-column width="180" label="操作">
          <template #default="scope">
            <el-button type="primary" plain style="display: block; margin-bottom: 5px;" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" style="margin-left: 0;" @click="handleDel(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination
          v-model:pageSize="data.pageSize"
          v-model:currentPage="data.pageNum"
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
          :page-sizes="[5, 10, 20, 50, 100]"
          :layout="'sizes, prev, pager, next'"
          :total="data.total"
      />
    </div>

    <el-dialog
        v-model="data.formVisible"
        title="材料信息"
        width="35%"
    >
      <el-form
          :model="data.form"
          style="padding-right: 30px"
          ref="formRef"
          :rules="rules"
          label-width="100px"
          label-position="right"
      >
        <el-form-item label="材料编码" prop="materialCode">
          <el-input v-model="data.form.materialCode" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="材料名称" prop="materialName">
          <el-input v-model="data.form.materialName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="材料类型" prop="materialType">
          <el-input v-model="data.form.materialType" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="物料类型" prop="materialCategory">
          <el-input v-model="data.form.materialCategory" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input v-model.number="data.form.quantity" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="单价" prop="unitCost">
          <el-input v-model.number="data.form.unitCost" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="data.formVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { Search } from '@element-plus/icons-vue';
import request from '@/utils/request';
import { ElMessage, ElMessageBox } from 'element-plus';

const data = reactive({
  materialName: '',
  materialType: '',
  materialCategory: '',
  tableData: [],
  form: {},
  total: 0,
  pageSize: 10,
  pageNum: 1,
  formVisible: false,
});

// 初始化页面加载
const load = () => {
  request.get('/material/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      materialName: data.materialName,
      materialType: data.materialType,
      materialCategory: data.materialCategory,
    },
  }).then(res => {
    data.tableData = res.data.list || [];
    data.total = res.data.total || 0;
  });
};
load();

// 翻页事件
const handleCurrentChange = (val) => {
  data.pageNum = val;
  load();
};
const handleSizeChange = (val) => {
  data.pageSize = val;
  load();
};

// 重置事件
const reset = () => {
  data.materialName = '';
  data.materialType = '';
  data.materialCategory = '';
  load();
};

// 新增事件弹窗
const handleAdd = () => {
  data.formVisible = true;
  data.form = {};
};

// 保存数据到后台
const rules = reactive({
  materialCode: [
    { required: true, message: "请输入材料编码", trigger: "blur" },
  ],
  materialName: [
    { required: true, message: "请输入材料名称", trigger: "blur" },
  ],
  materialType: [
    { required: true, message: "请输入材料类型", trigger: "blur" },
  ],
  materialCategory: [
    { required: true, message: "请输入物料类型", trigger: "blur" },
  ],
  quantity: [
    { required: true, message: "请输入数量", trigger: "blur" },
  ],
  unitCost: [
    { required: true, message: "请输入单价", trigger: "blur" },
  ],
});

const formRef = ref();
const save = () => {
  formRef.value.validate(valid => {
    if (valid) {
      request.request({
        url: data.form.id ? '/material/update' : '/material/add',
        method: data.form.id ? 'PUT' : 'POST',
        data: data.form,
      }).then(res => {
        if (res.code === '200') {
          load();
          ElMessage.success("操作成功！");
          data.formVisible = false;
          data.form = {};
        } else {
          ElMessage.error(res.msg);
        }
      });
    }
  });
};

// 编辑事件
const handleEdit = (row) => {
  data.form = { ...row };
  data.formVisible = true;
};

// 删除事件
const handleDel = (id) => {
  ElMessageBox.confirm('确定要删除吗?', '提示', { type: 'warning' })
      .then(() => {
        request.delete(`/material/delete/${id}`).then(res => {
          if (res.code === '200') {
            load();
            ElMessage.success("操作成功！");
          } else {
            ElMessage.error(res.msg);
          }
        });
      })
      .catch(() => {
        ElMessage({ type: 'info', message: '已取消删除' });
      });
};
</script>