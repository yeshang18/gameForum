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
// 批量获取用户
// 封禁用户
// 解封用户
// 增加积分
// 减少积分
// 增加经验
// 注销账户


/*---------------平台相关------------*/
//获取平台
export const getPlatformApi = (pageNo,pageSize) =>{
    return request.get('/platform/getAll',{params:{pageNo,pageSize}})
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
export const getGameTypeApi = (pageNo,pageSize) =>{
    return request.get('/gameType/getAll',{params:{pageNo,pageSize}})
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
export const getGameApi = (pageNo,pageSize) =>{
    return request.get('/game/getAll',{params:{pageNo,pageSize}})
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
// 根据标题获取帖子
export const getArticleByTitleApi = (title,pageNo,pageSize) =>{

    return request.get('/article/title',{params:{title,pageNo,pageSize}})
}
// 根据游戏获取帖子
export const getArticleByGameApi = (gameId,pageNo,pageSize) =>{
    return request.get('/article/game',{params:{gameId,pageNo,pageSize}})
}
// 删帖
// 发帖

/*---------------评论相关------------*/
// 获取所有评论
// 根据id查询
// 删除评论
// 发表评论

/*---------------点赞相关------------*/
// 点赞
// 获取点赞记录
// 取消点赞

/*---------------浏览记录相关------------*/
// 浏览
// 获取浏览记录




