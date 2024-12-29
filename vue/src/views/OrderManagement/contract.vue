<template>

  <div>

    <div class="card" style="margin-bottom: 10px">
      <el-input v-model="data.contractNumber" style="width: 300px; margin-right: 10px" placeholder="请输入合同编号查询" :prefix-icon="Search"/>
      <el-input v-model="data.contractName" style="width: 300px; margin-right: 10px" placeholder="请输入合同名称查询" :prefix-icon="Search"/>
      <el-button type="primary" style="margin-left: 10px" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">

      <div>
        <el-button type="primary" @click="handelAdd">新增</el-button>
      </div>

      <div>
        <el-table :data="data.tableData" style="width: 100%">
          <el-table-column prop="contractNumber" label="合同编号" width="100"></el-table-column>
          <el-table-column prop="contractName" label="合同名称" width="100"></el-table-column>
          <el-table-column prop="businessOpportunity" label="商机名称" width="100"></el-table-column>
          <el-table-column prop="customerCode" label="客户编号" width="100"></el-table-column>
          <el-table-column prop="customerName" label="客户名称" width="100"></el-table-column>
          <el-table-column prop="customerPhone" label="客户电话" width="100"></el-table-column>


          <el-table-column prop="salesOrderGenerated" label="是否生成销售订单" width="150">
            <template #default="scope">
              <el-button
                  type="success"
                  plain
                  @click="openOrderDialog(scope.row)"
                  :disabled="scope.row.salesOrderGenerated"
              >
                {{ scope.row.salesOrderGenerated ? '已生成' : '生成订单' }}
              </el-button>
            </template>
          </el-table-column>

          <el-table-column prop="invoiceIssued" label="是否开票" width="100">
            <template #default="scope">
              {{ scope.row.invoiceIssued ? '是' : '否' }}
            </template>
          </el-table-column>

          <el-table-column prop="totalAmount" label="合同总金额" width="100"></el-table-column>
          <el-table-column prop="startDate" label="签订日期" width="100"></el-table-column>
          <el-table-column prop="startDateTime" label="合同开始时间" width="120"></el-table-column>
          <el-table-column prop="endDateTime" label="合同结束时间" width="120"></el-table-column>
          <el-table-column prop="contractType" label="合同类型" width="100"></el-table-column>
          <el-table-column prop="partyA" label="甲方负责人" width="100"></el-table-column>
          <el-table-column prop="partyB" label="乙方负责人" width="100"></el-table-column>
          <el-table-column prop="remarks" label="备注" width="100"></el-table-column>
          <el-table-column prop="executionStage" label="执行阶段" width="100"></el-table-column>

          <el-table-column label="附件" width="120">
            <template #default="scope">
              <el-image
                  v-if="scope.row.attachmentUrl"
                  :src="scope.row.attachmentUrl"
                  :preview-src-list="[scope.row.attachmentUrl]"
                  style="width: 40px;height: 40px;border-radius: 5px"
                  preview-teleported>
              </el-image>
            </template>
          </el-table-column>


          <el-table-column width="100" label="操作">
            <template #default="scope">
              <el-button
                  type="primary"
                  plain
                  style="width: 50px;"
                  @click="handelEdit(scope.row)"
                  v-if="!scope.row.salesOrderGenerated"
              >编辑</el-button>
              <el-button
                  type="danger"
                  style="margin-left: 0; width: 50px;"
                  @click="handelDelete(scope.row)"
                  v-if="!scope.row.salesOrderGenerated"
              >删除</el-button>
            </template>
          </el-table-column>

        </el-table>
      </div>

    </div>

    <div class="card">
      <el-pagination
          v-model:pageSize="data.pageSize"
          v-model:currentPage="data.pageNum"
          @current-change="handelCurrentChange"
          @size-change="handelSizeChange"
          :page-sizes="[5, 10, 20, 50, 100]"
          :layout="'sizes, prev, pager, next'"
          :total="data.total">
      </el-pagination>
    </div>


    <el-dialog v-model="data.formVisible" title="合同信息" width="50%">

      <el-form :model="data.form" style="padding-right: 30px" ref="formRef" :rules="rules" label-width="120" label-position="right">
        <el-form-item label="合同编号" prop="contractNumber">
          <el-input v-model="data.form.contractNumber" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="合同名称" prop="contractName">
          <el-input v-model=
                        "data.form.contractName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商机名称" prop="businessOpportunity">
          <el-input v-model="data.form.businessOpportunity" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="客户编号" prop="customerCode">
          <el-input v-model="data.form.customerCode" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="客户名称" prop="customerName">
          <el-input v-model="data.form.customerName" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="客户电话" prop="customerPhone">
          <el-input v-model="data.form.customerPhone" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="是否生成销售订单" prop="salesOrderGenerated">
          <el-switch v-model="data.form.salesOrderGenerated" disabled></el-switch>
        </el-form-item>

        <el-form-item label="是否开票" prop="invoiceIssued">
          <el-switch v-model="data.form.invoiceIssued"></el-switch>
        </el-form-item>

        <el-form-item label="合同总金额" prop="totalAmount">
          <el-input v-model="data.form.totalAmount" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="签订日期" prop="startDate">
          <el-date-picker format="YYYY-MM-DD" value-format="YYYY-MM-DD" v-model="data.form.startDate"></el-date-picker>
        </el-form-item>

        <el-form-item label="合同开始时间" prop="startDateTime">
          <el-date-picker format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" v-model="data.form.startDateTime"></el-date-picker>
        </el-form-item>

        <el-form-item label="合同结束时间" prop="endDateTime">
          <el-date-picker format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" v-model="data.form.endDateTime"></el-date-picker>
        </el-form-item>

        <el-form-item label="合同类型" prop="contractType">
          <el-select v-model="data.form.contractType" placeholder="请选择合同类型">
            <el-option label="采购" value="purchase"></el-option>
            <el-option label="销售" value="sale"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="甲方负责人" prop="partyA">
          <el-input v-model="data.form.partyA" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="乙方负责人" prop="partyB">
          <el-input v-model="data.form.partyB" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="备注" prop="remarks">
          <el-input type="textarea" v-model="data.form.remarks" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="执行阶段" prop="executionStage">
          <el-input v-model="data.form.executionStage" value="未开始" readonly></el-input>
        </el-form-item>

        <el-form-item label="附件上传">
          <el-upload action="http://localhost:9090/uploadFile" list-type="picture-card" :on-success="handleImgUploadSuccess" :file-list="data.form.fileList">
            <el-button type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>

      </el-form>


      <span slot="footer" class="dialog-footer">
        <el-button @click="data.formVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>

    </el-dialog>

    <el-dialog v-model="data.orderFormVisible" title="生成订单" width="50%">
      <el-form :model="data.orderForm" style="padding-right: 30px" ref="orderFormRef" label-width="120" label-position="right">

        <el-form-item label="订单编号" prop="orderID">
          <el-input v-model="data.orderForm.orderID" autocomplete="off" ></el-input>
        </el-form-item>

        <el-form-item label="合同名称" prop="contractName">
          <el-input v-model="data.orderForm.contractName" autocomplete="off" readonly></el-input>
        </el-form-item>

        <el-form-item label="客户编号" prop="customerID">
          <el-input v-model="data.orderForm.customerID" autocomplete="off" readonly></el-input>
        </el-form-item>

        <el-form-item label="合同总金额" prop="totalAmount">
          <el-input v-model="data.orderForm.totalAmount" autocomplete="off" readonly></el-input>
        </el-form-item>

        <el-form-item label="合同编号" prop="contractNumber">
          <el-input v-model="data.orderForm.contractNumber" autocomplete="off" readonly></el-input>
        </el-form-item>


        <el-form-item label="产品名称" prop="productName">
          <el-select v-model="data.orderForm.productName" placeholder="请选择产品名称">
            <el-option label="产品A" value="产品A"></el-option>
            <el-option label="产品B" value="产品B"></el-option>
            <el-option label="产品B" value="产品C"></el-option>
          </el-select>
        </el-form-item>


        <el-form-item label="产品数量" prop="productSum">
          <el-input v-model="data.orderForm.productSum" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="订单类型" prop="orderType">
          <el-input v-model="data.orderForm.orderType" autocomplete="off" readonly></el-input>
        </el-form-item>

        <el-form-item label="是否生成生产计划" prop="generateProductionPlan">
          <el-switch
              v-model="data.orderForm.generateProductionPlan"
              disabled
          ></el-switch>
        </el-form-item>


        <el-form-item label="订单开始时间" prop="startTime">
          <el-date-picker format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" v-model="data.orderForm.startTime"></el-date-picker>
        </el-form-item>

        <el-form-item label="订单结束时间" prop="endTime">
          <el-date-picker format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" v-model="data.orderForm.endTime"></el-date-picker>
        </el-form-item>

        <el-form-item label="订单状态" prop="status">
          <el-input v-model="data.orderForm.status"  value="未开始" readonly>
          </el-input>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="data.orderFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="generateOrder">确 定</el-button>
  </span>
    </el-dialog>

  </div>
</template>



<script setup>
import { ref, reactive } from "vue";
import { Search } from "@element-plus/icons-vue";
import request from "@/utils/request";
import { ElMessage, ElMessageBox } from "element-plus";

const data = reactive({
  contractNumber: '',
  contractName: '',
  tableData: [],
  form: {
    attachmentUrl: '',
    fileList: [],
    executionStage:'未开始'
  },
  total: 0,
  pageSize: 5,
  pageNum: 1,
  formVisible: false,
  currentRowIdentifier: '', // 保存当前行的唯一标识符（合同编号）
  orderForm: {
    customerID:'',
    contractNumber: '',
    contractName: '',
    totalAmount: '',
    generateProductionPlan:'false',
    startTime: '',
    endTime: '',
    orderType:'',
    orderID:'',
    status: '未开始',
    productName:'',
    productSum:'',
  },
  orderFormVisible: false,
});

// 打开生成订单的弹窗
const openOrderDialog = (row) => {
  data.orderForm = {
    customerID: row.customerCode,        // 客户编号
    contractNumber: row.contractNumber,  // 合同编号
    contractName: row.contractName,      // 合同名称
    totalAmount: row.totalAmount,        // 总金额
    orderType: row.contractType,         //订单类型
    startTime: '',                       // 开始时间
    endTime: '',                         // 结束时间
    orderID:'',                          // 订单ID
    status: '',                          // 订单状态
    productName:'',                      // 产品名称
    productSum:'',                       // 产品数量
  };
  data.orderFormVisible = true;
  data.currentRowIdentifier = row.contractNumber; // 保存当前行的合同编号
};

// 生成订单
const generateOrder = () => {
  data.orderForm.status='未开始';
  if (!data.orderForm.orderID || !data.orderForm.startTime || !data.orderForm.endTime || !data.orderForm.status || !data.orderForm.productName || !data.orderForm.productSum) {
    ElMessage.error("请填写所有必填项");
    return;
  }

  request.post("/order/add", data.orderForm).then((res) => {
    if (res.code === '200') {
      ElMessage.success("订单生成成功！");
      // 使用合同编号找到对应的行并更新 salesOrderGenerated 字段为 true
      const index = data.tableData.findIndex(item => item.contractNumber === data.currentRowIdentifier);
      if (index !== -1) {
        // 构建更新数据对象
        const updateData = {
          contractNumber: data.currentRowIdentifier,
          salesOrderGenerated: true,
          executionStage: '开始中' // 设置执行阶段为开始中
        };
        // 调用后端 API 更新合同状态
        request.put("/contract/update2", updateData).then((res) => {
          if (res.code === '200') {
            // 更新成功后，刷新表格视图
            data.tableData[index].salesOrderGenerated = true;
            data.tableData[index].executionStage = '开始中'; // 更新执行阶段
            data.tableData = [...data.tableData];
            data.orderFormVisible = false;
            load(); // 重新加载合同列表
          } else {
            ElMessage.error("合同状态更新失败：" + res.msg);
          }
        });
      }
    } else {
      ElMessage.error(res.msg);
    }
  });
};

// 初始化页面加载
const load = () => {
  request.get("/contract/selectPage", {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      contractNumber: data.contractNumber,
      contractName: data.contractName,
    },
  }).then((res) => {
    data.tableData = res.data?.list || [];
    data.total = res.data?.total || 0;
    data.tableData.forEach((item) => {
      // 直接使用原始 URL
      item.attachmentUrl = item.attachmentUrl; // 确保 URL 是有效的
    });
  });
};
load();

// 翻页事件
const handelCurrentChange = () => {
  load();
};

const handelSizeChange = () => {
  load();
};

// 重置事件
const reset = () => {
  data.contractName = '';
  data.contractNumber = '';
  load();
};

// 新增事件弹窗
const handelAdd = () => {
  data.formVisible = true;
  data.form = {
    attachmentUrl: '',
    fileList: []
  };
};

// 保存数据到后台
const rules = reactive({
  contractNumber: [
    { required: true, message: "请输入合同编号", trigger: "blur" },
  ],
  contractName: [
    { required: true, message: "请输入合同名称", trigger: "blur" },
  ],
  businessOpportunity: [
    { required: true, message: "请输入商机名称", trigger: "blur" },
  ],
  customerCode: [
    { required: true, message: "请输入客户编号", trigger: "blur" },
  ],
  customerName: [
    { required: true, message: "请输入客户名称", trigger: "blur" },
  ],
  customerPhone: [
    { required: true, message: "请输入客户电话", trigger: "blur" },
  ],
  totalAmount: [
    { required: true, message: "请输入合同总金额", trigger: "blur" },
  ],
  startDate: [
    { required: true, message: "请选择签订日期", trigger: "change" },
  ],
  startDateTime: [
    { required: true, message: "请选择合同开始时间", trigger: "change" },
  ],
  endDateTime: [
    { required: true, message: "请选择合同结束时间", trigger: "change" },
  ],
  contractType: [
    { required: true, message: "请选择合同类型", trigger: "change" },
  ],
  partyA: [
    { required: true, message: "请输入甲方负责人", trigger: "blur" },
  ],
  partyB: [
    { required: true, message: "请输入乙方负责人", trigger: "blur" },
  ],
  remarks: [
    { required: true, message: "请输入备注", trigger: "blur" },
  ],

});

const formRef = ref();

const save = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      const url = data.form.id ? "/contract/update" : "/contract/add";
      const method = data.form.id ? "PUT" : "POST";
      data.form.executionStage = "未开始";
      request.request({
        url: url,
        method: method,
        data: data.form,
      }).then((res) => {
        if (res.code === '200') {
          // 重新获取数据
          load();
          ElMessage.success("操作成功！");
          data.formVisible = false;
          data.form = {
            attachmentUrl: '',
            fileList: []
          };
        } else {
          ElMessage.error(res.msg);
        }
      });
    }
  });
};

// 编辑事件
const handelEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row));
  data.formVisible = true;
};

// 删除事件
const handelDelete = (row) => {
  ElMessageBox.confirm("确定要删除吗?", "提示", { type: "warning" }).then(() => {
    request.delete('/contract/delete/' + row.id).then((res) => {
      if (res.code === '200') {
        load();
        ElMessage.success("删除成功！");
        data.formVisible = false;
      } else {
        ElMessage.error(res.msg);
      }
    });
  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '已取消删除'
    });
  });
};

// 上传附件成功
const handleImgUploadSuccess = (response, file) => {
  if (response.code === '200' && response.data) {
    data.form.attachmentUrl = response.data;
    ElMessage.success('附件上传成功');
  } else {
    ElMessage.error('附件上传失败，请重试');
  }
};
</script>

<style scoped>
.card {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}
</style>


