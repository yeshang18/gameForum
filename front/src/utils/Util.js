import { ref } from "vue";

const levelInfo =ref({

})



export default {
    level : (level)=>{
        if(level<99){
            levelInfo.info="lv1 "+level+"/99";
            levelInfo.color="#86909c"
            return levelInfo;
        }
        else if(level>=99&&level<299){
            levelInfo.info="lv2 "+level+"/299";
            levelInfo.color="green"
            return levelInfo;

        }
        else if(level>=299&&level<599){

            levelInfo.info="lv3 "+level+"/599";
            levelInfo.color="aqua"
            return levelInfo;
        }
        else if(level>=599&&level<999){

            levelInfo.info="lv4 "+level+"/999";
            levelInfo.color="orange"
            return levelInfo;
        }
        else if(level>=999){

            levelInfo.info="lv5 "+level+"/max";
            levelInfo.color="red"
            return levelInfo;
        }
    }
}