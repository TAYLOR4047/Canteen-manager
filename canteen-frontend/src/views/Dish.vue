<template>
  <!--     页面主体       -->
    <div>
        <!--        搜索部分        -->
        <div style="margin: 10px 0">
            <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search"
                      v-model="title"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
        </div>

        <!--      表格外部操作部分          -->
        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <el-popconfirm
                    class="ml-5"
                    confirm-button-text='确定'
                    cancel-button-text='我再想想'
                    icon="el-icon-info"
                    icon-color="red"
                    title="您确定批量删除这些数据吗？"
                    @confirm="delBatch"
            >
                <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>
        </div>

        <!--        表格内部操作部分        -->
        <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"
                  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="id" label="ID" width="80"></el-table-column>
            <el-table-column prop="title" label="餐品名称"></el-table-column>
            <el-table-column prop="price" label="价格"></el-table-column>
            <el-table-column prop="status" label="餐品状态"
            :filters="[{text:'上架',value:1},{text:'下架',value:0}]"
            :filter-method="filterStatus"
            filter-placement="bottom-end">
                <template slot-scope="scope">
                    <el-tag
                        :type="scope.row.status === 1 ? 'primary' : 'danger'"
                        disable-transitions>{{scope.row.status === 1 ? '上架' : '下架'}}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="280" align="center">
                <template slot-scope="scope">
                    <el-button type="info" @click="selectMenu(scope.row)">餐品分配 <i class="el-icon-menu"></i>
                    </el-button>
                    <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i>
                    </el-button>
                    <el-popconfirm
                            class="ml-5"
                            confirm-button-text='确定'
                            cancel-button-text='我再想想'
                            icon="el-icon-info"
                            icon-color="red"
                            title="您确定删除吗？"
                            @confirm="del(scope.row.id)">
                        <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i>
                        </el-button>
                    </el-popconfirm>
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

        <el-dialog title="新增餐品详情" :visible.sync="dialogFormVisible" width="30%">
            <el-form label-width="80px" size="small">
                <el-form-item label="餐品名称">
                    <el-input v-model="form.title" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="餐品价格">
                    <el-input v-model="form.price" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="餐品状态">
                    <el-input v-model="form.status" autocomplete="off"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="餐品分类所属分配" :visible.sync="menuDialogVis" width="30%">
            <el-tree
                    :props="props"
                    :data="menuData"
                    show-checkbox
                    node-key="id"
                    ref="tree"
                    :default-expanded-keys="expends"
                    :default-checked-keys="checks">
                <span class="custom-tree-node" slot-scope="{ node, data }">
                    <span><i :class="data.icon"></i> {{ data.name }}</span>
                </span>
            </el-tree>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<!--页面数据与动作Js代码-->
<script>
export default {
    name: "Type",
    data() {
        return {
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            title: "",
            form: {},
            dialogFormVisible: false,
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
        save() {
            this.request.post("/dish", this.form).then(res => {
                if (res) {
                    this.$message.success("保存成功")
                    this.dialogFormVisible = false
                    this.load()
                } else {
                    this.dialogFormVisible = false
                    this.load()
                    this.$message.error("保存失败")
                }
            })
        },
        saveRoleMenu() {
            this.request.post("/dish/dishType/" + this.dishId, this.$refs.tree.getCheckedKeys()).then(res => {
                if (res.code === '200') {
                    this.$message.success("绑定成功")
                    this.menuDialogVis = false
                } else {
                    this.$message.error(res.msg)
                    this.menuDialogVis = false
                }
            })
        },
        handleAdd() {
            this.dialogFormVisible = true
            this.form = {}
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
        changeEnable(row) {
            this.request.post("/dish/update", row).then(res => {
                if (res.code === '200') {
                    this.$message.success("操作成功")
                }
            })
        },
        selectMenu(role) {
            this.menuDialogVis = true;
            this.roleId = role.id;
            this.roleFlag = role.flag;
            // 请求菜单数据
            this.request.get("/menu", {}).then(res => {
                console.log(res);
                this.menuData = res.data;
                // 把后台返回的菜单数据处理成id数组
                this.expends = this.menuData.map(v => v.id)
            })
            this.request.get("/role/roleMenu/" + this.roleId).then(res => {
                // 先渲染弹窗里的元素
                this.menuDialogVis = true
                this.checks = res.data;
                this.request.get("/menu/ids").then(r => {
                    const ids = r.data
                    ids.forEach(id => {
                        if (!this.checks.includes(id)) {
                            this.$refs.tree.setChecked(id, false)
                        }
                    })
                })
            })
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