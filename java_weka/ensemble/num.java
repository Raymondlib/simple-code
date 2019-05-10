import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.Bagging;
import weka.classifiers.meta.Vote;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ArffLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

//看看分类器数量的影响，采用3-300，间隔2，取值
public class num {
    public static void writeLog(String filepath,String s){
        try{
            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(s);
            bw.close();
            fw.close();
        }catch (Exception  e){
            e.printStackTrace();
        }
    }
    public static void f(int num,String cf){
        try {
        Instances trainIns = null;
        Classifier cfs = null;
        cfs = (Classifier)Class.forName(cf).newInstance();
        Classifier[] cfsArray = new Classifier[num];
        for (int j=0;j<num;j++){
            cfsArray[j]=cfs;
        }
        Vote ensemble = new Vote();

        SelectedTag tag1 = new SelectedTag(Vote.MAJORITY_VOTING_RULE, Vote.TAGS_RULES);

        ensemble.setCombinationRule(tag1);
        ensemble.setClassifiers(cfsArray);
        //设置随机数种子
        ensemble.setSeed(2);
        String path ="C:\\Users\\25755\\Desktop\\1级队列\\数据集\\";
        ArrayList<String> files =new ArrayList<String>();
        File file = new File(path);
        File [] tempList = file.listFiles();
        for (int i=0;i<1;i++){
            if(tempList[i].isFile()){
                files.add(tempList[i].toString());
                File a= tempList[i];


                ArffLoader loader = new ArffLoader();
                loader.setFile(a);
                trainIns = loader.getDataSet();

                trainIns.setClassIndex(trainIns.numAttributes()-1);
//                ensemble.buildClassifier(trainIns);

                Evaluation eva =new Evaluation(trainIns);
                eva.crossValidateModel(ensemble,trainIns,10,new Random(1));
                //eva.evaluateModelOnceAndRecordPrediction(ensemble,trainIns)

                System.out.println(eva.toSummaryString());
//                    System.out.println(eva.errorRate());
//                    System.out.println(eva.avgCost());
//                    System.out.println(eva.correct());
                System.out.println(1-eva.errorRate());
                writeLog("log.txt",(1-eva.errorRate())+"\n");
//                accurate.add(1-eva.errorRate());
//                    System.out.println();
//                    System.out.println(eva.correlationCoefficient());
                System.out.println(eva.rootMeanSquaredError());
//                    System.out.println(eva.kappa());
                System.out.println(tempList[i].toString());

            }

        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
//        Classifier cfs1=null;
//        cfs1 = (Classifier)Class.forName("weka.classifiers.bayes.NaiveBayes").newInstance();
        try{
        Instances trainIns = null;
        String path ="C:\\Users\\25755\\Desktop\\1级队列\\数据集\\anneal.arff";

        File a= new File(path);
        ArffLoader loader = new ArffLoader();
        loader.setFile(a);
        trainIns = loader.getDataSet();
        trainIns.setClassIndex(trainIns.numAttributes()-1);
        String NaiveBayes="weka.classifiers.bayes.NaiveBayes";
        String J48="weka.classifiers.trees.J48";
        String zeroR="weka.classifiers.rules.ZeroR";
        String RandomForest="weka.classifiers.trees.RandomForest";
        String DecisionStump ="weka.classifiers.trees.DecisionStump";
        String RandomTree ="weka.classifiers.trees.RandomTree";
        String REPTree ="weka.classifiers.trees.REPTree";
        String DecisionTable="weka.classifiers.rules.DecisionTable";
        String [] cfs_real ={NaiveBayes,J48,zeroR,RandomForest,DecisionStump,RandomTree,REPTree,DecisionTable};
//            System.out.println(cfs_name);
//            for(String str: cfs_name)
//            {
//                System.out.println(str);
//            }
         String [] cfs_name ={"NaiveBayes","J48","zeroR","RandomForest","DecisionStump","RandomTree","REPTree","DecisionTable"};


            Classifier cfs = (Classifier)Class.forName(DecisionStump).newInstance();
            Classifier c = new Bagging();
            ((Bagging) c).setClassifier(cfs);
            ((Bagging) c).setNumIterations(3);
            ((Bagging) c).setBagSizePercent(66);
//            c.buildClassifier(trainIns);
            Evaluation eva =new Evaluation(trainIns);
            eva.crossValidateModel(c,trainIns,10,new Random(1));
            System.out.println(1-eva.errorRate());
            System.out.println();
//            eva.crossValidateModel(c,trainIns,10,new Random(1));
//            System.out.println(eva.toSummaryString());
//            System.out.println(1-eva.errorRate());
//            System.out.println(eva.rootMeanSquaredError());
        for (int s=0;s<cfs_real.length;s++){
            String classify_name =cfs_real[1];

            cfs =(Classifier)Class.forName(classify_name).newInstance();
            for(int k =3;k<5;k+=2){
                c = new Bagging();
                ((Bagging) c).setNumIterations(k);
                ((Bagging) c).setClassifier(cfs);
                ((Bagging) c).setBagSizePercent(66);

                eva.crossValidateModel(c,trainIns,10,new Random(1));
                System.out.println(1-eva.errorRate());
                writeLog(cfs_name[s]+".txt",(1-eva.errorRate())+"\n");
            }

        }

        }catch (Exception e){
            e.printStackTrace();
        }


//        f(3,DecisionStump);
//        for (int k=3;k<300;k+=2){
//            f(k,DecisionStump);
//        }

    }

}