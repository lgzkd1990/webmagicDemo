package cn.itcast.demo;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.RedisScheduler;

public class MyProcessor implements PageProcessor {

    public void process(Page page) {
        //System.out.println(page.getHtml().toString());
        //page.addTargetRequests(page.getHtml().links().all());
        page.addTargetRequests(page.getHtml().links().regex("https://blog.csdn.net/[a-z 0-9 -]+/article/details/[0-9]{8}").all());
        //System.out.println(page.getHtml().xpath("//*[@id=\"nav\"]/div/div/ul/li[5]/a").toString());
        //System.out.println(page.getHtml().xpath("//*[@id=\"mainBox\"]/main/div[1]/div/div/div[1]/h1").toString());
page.putField("title",page.getHtml().xpath("//*[@id=\"mainBox\"]/main/div[1]/div/div/div[1]/h1").toString());
    }

    public Site getSite() {
        return Site.me().setSleepTime(100).setRetryTimes(3);
    }

    public static void main(String[] args) {
        Spider.create(
                new MyProcessor()
        ).addUrl("https://blog.csdn.net")
                .addPipeline(new ConsolePipeline())
                .addPipeline(new FilePipeline("D:\\Project\\十次方项目\\人工智能\\webmagicDemo\\src\\main\\resources\\data"))
                .addPipeline(new JsonFilePipeline("D:\\Project\\十次方项目\\人工智能\\webmagicDemo\\src\\main\\resources\\json"))
                .addPipeline((new MyPipeline()))
                //.setScheduler(new QueueScheduler())
                //.setScheduler(new FileCacheQueueScheduler("D:\\Project\\十次方项目\\人工智能\\webmagicDemo\\src\\main\\resources\\scheduler"))
                .setScheduler(new RedisScheduler("127.0.0.1"))
                .run();
    }


}
