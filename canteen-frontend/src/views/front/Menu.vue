<template>
  <!--     页面主体       -->
    <div>

        <!--        搜索部分        -->
        <div style="margin: 10px 0">
            <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search"
                      v-model="title"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
            <el-tabs :tab-position="top" style="height: 30px;">
                <el-tab-pane label="披萨"></el-tab-pane>
                <el-tab-pane label="牛排"></el-tab-pane>
                <el-tab-pane label="意面"></el-tab-pane>
                <el-tab-pane label="甜品"></el-tab-pane>
                <el-tab-pane label="拼盘"></el-tab-pane>
                <el-tab-pane label="开胃菜"></el-tab-pane>
                <el-tab-pane label="鲜榨果汁"></el-tab-pane>
                <el-tab-pane label="现磨咖啡"></el-tab-pane>
                <el-tab-pane label="酒类"></el-tab-pane>
            </el-tabs>
        </div>

        <!--        表格内部操作部分        -->
        <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"
                  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column label="图片预览" width="80">
                <template slot-scope="scope">
                    <el-popover placement="top-start" title="" trigger="hover">
                        <img :src="scope.row.image" alt="" style="width: 150px;height: 150px">
                        <img slot="reference" :src="scope.row.image" style="width: 30px;height: 30px">
                    </el-popover>
                </template>
            </el-table-column>
            <el-table-column prop="title" label="餐品名称"></el-table-column>
            <el-table-column prop="price" label="价格"></el-table-column>
            <el-table-column label="操作" width="280" align="center">
                <template slot-scope="scope">
                    <el-button type="danger" @click="sendToCart(scope.row)">加入购物车 <i class="el-icon-remove-outline"></i>
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

        <!--       翻页与页码部分         -->
        <div style="padding: 10px 0">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-sizes="[2, 5, 10, 20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<!--页面数据与动作Js代码-->
<script>
export default {
    name: "Front-Menu",
    data() {
        return {
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            title: "",
            form: {},
            dialogFormVisible: false,
            dialogInsertFormVisible: false,
            menuDialogVis: false,
            multipleSelection: [],
            menuData: [],
            props: {
                label: 'title',
            },
            expends: [],
            checks: [],
            roleId: 0,
            roleFlag: '',
        }
    },
    // 请求分页查询数据
    created() {
        this.load()
    },
    methods: {
        // 将数据库查询操作封装
        load() {
            this.request.get("/dish/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    title: this.title,
                }
            }).then(res => {
                console.log(res)
                this.tableData = res.records
                this.total = res.total

            });
        },
        handleAdd() {
            this.dialogInsertFormVisible = true
            this.form = {}
        },
        sendToCart(dishrow){

        },
        handleEdit(row) {
            this.form = row
            this.dialogFormVisible = true
        },
        del(id) {
            this.request.delete("/dish/" + id).then(res => {
                if (res) {
                    this.$message.success("删除成功")
                    this.load()
                } else {
                    this.$message.error("删除失败")
                }
            })
        },
        handleSelectionChange(val) {
            console.log(val)
            this.multipleSelection = val
        },
        delBatch() {
            let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
            this.request.post("/dish/del/batch", ids).then(res => {
                if (res) {
                    this.$message.success("批量删除成功")
                    this.load()
                } else {
                    this.$message.error("批量删除失败")
                }
            })
        },
        reset() {
            this.title = ""
            this.load()
        },
        // 动态分页请求
        handleSizeChange(pageSize) {
            console.log(pageSize)
            this.pageSize = pageSize
            this.load()
        },
        handleCurrentChange(pageNum) {
            console.log(pageNum)
            this.pageNum = pageNum
            this.load()
        },
        filterStatus(value, row) {
            return row.status === value;
        },
    }
}
</script>

<!--表格头部样式-->
<style>
.headerBg {
    background: #eee !important;
}
</style>