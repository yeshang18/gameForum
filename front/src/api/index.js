import { id } from "@kangc/v-md-editor";
import axios from "../http/request";
import request from "../http/request";

/*---------------用户相关------------*/
//注册
export const userRegisterApi = (data) =>{
    return request.post('/user/register',data,{
    })
}

//登陆
export const loginApi = (data) =>{
    return request.post('/user/login',data,{
    })
}
//获取用户信息
export const getUserByIdApi = (id) =>{
    return request.get('/user/'+id,{
    })
}
// 修改信息
export const updateUserApi = (data) =>{
    return request.put('/user/update',data,{
    })
}
// 批量获取用户
// 封禁用户
export const stopUserApi = (data) =>{
    return request.put('/user/status/0',data,{
    })
}
// 解封用户
export const openUserApi = (data) =>{
    return request.put('/user/status/1',data,{
    })
}
// 增加积分
export const addintegralApi = (num) =>{
    return request.put('/user/integral/add?num='+num,{
    })
}
// 减少积分
export const reduceintegralApi = (num) =>{
    return request.put('/user/integral/reduce?num='+num,{
    })
}
// 增加经验
export const addLevelApi = (data) =>{
    return request.put('/user/level',data,{headers:{
        'Content-Type':'multipart/form-data'
    }})
}
// 注销账户
export const deleteUserApi = (data) =>{
    return request.delete('/user/level',data,{
    })
}

/*---------------平台相关------------*/
//获取平台
export const getPlatformApi = (pageNo,pageSize,status) =>{
    return request.get('/platform/getAll',{params:{pageNo,pageSize,status}})
}
// 添加平台
export const addPlatformApi = (data) =>{
    return request.post('/platform/add',data,{
    })
}
// 删除平台
export const deletePlatformApi = (id) =>{
    return request.delete('/platform/delete?id='+id,{
    })
}
// 更新平台信息
export const updatePlatformApi = (data) =>{
    return request.put('/platform/update',data,{
    })
}


/*---------------游戏类型相关------------*/
//获取游戏类型
export const getGameTypeApi = (pageNo,pageSize,status) =>{
    return request.get('/gameType/getAll',{params:{pageNo,pageSize,status}})
}
//添加游戏类型
export const addGameTypeApi = (data) =>{
    return request.post('/gameType/add',data,{
    })
}
//删除游戏类型
export const deleteGameTypeApi = (id) =>{
    return request.delete('/gameType/delete?id='+id,{
    })
}
//更新游戏类型
export const updateGameTypeApi = (data) =>{
    return request.put('/gameType/update',data,{
    })
}

/*---------------游戏相关------------*/
//添加游戏
export const addGameApi = (data) =>{
    return request.post('/game/add',data,{
    })
}
//删除游戏
export const deleteGameApi = (id) =>{
    return request.delete('/game/delete?id='+id,{
    })
}
// 获取所有游戏
export const getGameApi = (pageNo,pageSize,status) =>{
    return request.get('/game/getAll',{params:{pageNo,pageSize,status}})
}
// 根据id获取游戏
export const getGameByIdApi = (id) =>{
    return request.get('/game/getById?id='+id,{
    })
}
// 根据名称获取游戏
export const getGameByNameApi = (name,pageNo,pageSize) =>{
    return request.get('/game/getByName',{params:{name,pageNo,pageSize}})
}
// 根据平台获取游戏
export const getGameByPlatformApi = (platformId,pageNo,pageSize) =>{
    return request.get('/game/getByPf',{params:{platformId,pageNo,pageSize}}
    )
}
// 根据类型获取游戏
export const getGameByTypeApi = (typeId,pageNo,pageSize) =>{
    return request.get('/game/getByType',{params:{typeId,pageNo,pageSize}})
}
// 更新游戏信息
export const updateGameApi = (data) =>{
    return request.put('/game/update',data,{
    })
}
/*---------------帖子相关------------*/
// 获取全部帖子
export const getAllArticleApi = () =>{
    return request.get('/article/all',{
    })
}
//根据Id获取帖子
export const getArticleByIdApi = (articleId)=>{
    return request.get('/article/'+articleId,{})
}
//根据用户获取帖子
export const getArticleByUserApi = (byUserId,pageNo,pageSize)=>{
    return request.get('/article/byUser',{params:{byUserId,pageNo,pageSize}})
}

// 根据标题获取帖子
export const getArticleByTitleApi = (title,pageNo,pageSize) =>{

    return request.get('/article/title',{params:{title,pageNo,pageSize}})
}
// 根据游戏获取帖子
export const getArticleByGameApi = (gameId,pageNo,pageSize,articleTypeId) =>{
    return request.get('/article/game',{params:{gameId,pageNo,pageSize,articleTypeId}})
}
// 删帖
export const deleteArticleApi = (data) =>{
    return request.delete('/article/delete',{data})
}
// 发帖
export const saveArticleApi = (data) =>{
    return request.post('/article/save',data)
}
//置顶
export const setTopArticleApi = (data) =>{
    return request.put('/article/setTop',data)
}
//取消置顶
export const unTopArticleApi = (data) =>{
    return request.put('/article/unTop',data)
}

/*---------------文章类型相关------------*/
//获取文章类型
export const getArticleTypeApi = (pageNo,pageSize) =>{
    return request.get('/articleType/getAll',{params:{pageNo,pageSize}})
}
//添加文章类型
export const addArticleTypeApi = (data) =>{
    return request.post('/articleType/add',data,{
    })
}
//删除文章类型
export const deleteArticleTypeApi = (data) =>{
    return request.delete('/articleType/delete',data,{
    })
}
//更新文章类型
export const updateArticleTypeApi = (data) =>{
    return request.put('/articleType/update',data,{
    })
}



/*---------------评论相关------------*/
// 获取所有评论
export const getCommentByArticleApi = (articleId,order,pageNo,pageSize) =>{
    return request.get('/comment/byArticle',{params:{articleId,order,pageNo,pageSize}})
}
// 根据id查询
export const getCommentByIdApi = (id) =>{
    return request.get('/comment/byId?id='+id,{})
}
// 删除评论
export const deleteCommentApi = (id) =>{
    return request.delete('/comment/delete?id='+id,{})
}
// 发表评论
export const saveCommentApi = (data) =>{
    return request.post('/comment/save',data,{})
}
//置顶
export const setTopCommentApi = (data) =>{
    return request.put('/comment/setTop',data)
}
//取消置顶
export const unTopCommentApi = (data) =>{
    return request.put('/comment/unTop',data)
}

/*---------------点赞相关------------*/
// 点赞
export const saveLikeApi=(data)=>{
    return request.post('/likeRecord/save',data,{
    })
}
// 获取点赞记录
// 取消点赞
export const deleteLikeApi=(data)=>{
    return request.post('/likeRecord/delete',data,{
    })
}

/*---------------浏览记录相关------------*/
// 浏览
export const saveViewApi=(data)=>{
    return request.post('/viewRecord/save',data,{
    })
}
// 获取浏览记录


/*---------------文件相关------------*/
// 上传文件
export const uploadFileApi=(data)=>{
    return request.post('/file/upload',data,{headers:{
        'Content-Type':'multipart/form-data'
    }
    })
}
// 下载文件
export const downloadFileApi=(data)=>{
    return request.get('/file/download?img = '+img,{
    })
}


/*---------------论坛设置相关------------*/
//获取论坛设置
export const getforumSettingApi = (pageNo,pageSize) =>{
    return request.get('/forumSetting/getAll',{params:{pageNo,pageSize}})
}
//添加论坛设置
export const addForumSettingApi = (data) =>{
    return request.post('/forumSetting/add',data,{
    })
}
//删除论坛设置
export const deleteForumSettingApi = (data) =>{
    return request.delete('/forumSetting/delete',data,{
    })
}
//更新论坛设置
export const updateForumSettingApi = (data) =>{
    return request.put('/forumSetting/update',data,{
    })
}



