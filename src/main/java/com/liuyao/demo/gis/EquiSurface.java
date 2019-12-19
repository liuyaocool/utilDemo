package net.zhuolutech.geotoolsdemo.contour.services;




import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vividsolutions.jts.geom.Geometry;

import net.zhuolutech.geotoolsdemo.contour.Utils.FileUtil;
import org.geotools.data.FeatureSource;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import wcontour.Contour;
import wcontour.global.Border;
import wcontour.global.PointD;
import wcontour.global.PolyLine;
import wcontour.global.Polygon;
import wcontour.Interpolate;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;


/**
 * @author Lyu
 * @date 2019/11/5  14:37
 * @Description: 等高线实现
 **/
public class EquiSurface {


    /**
     * 生成等值面
     *
     * @param trainData    训练数据
     * @param dataInterval 数据间隔
     * @param size         大小，宽，高
     * @param brounds      边界
     * @param isclip       是否裁剪
     * @return
     */
    public String calEquiSurface(double[][] trainData,
                                 double[] dataInterval,
                                 int[] size,
                                 double[] brounds,
                                 boolean isclip) {
        String geojsonpogylon = "";
        try {
            double _undefData = -9999.0;

            List<PolyLine> cPolylineList = new ArrayList<PolyLine>();
            List<Polygon> cPolygonList = new ArrayList<Polygon>();

            int width = size[0],
                    height = size[1];
            double[] _X = new double[width];
            double[] _Y = new double[height];

//            File file = new File(boundryFile);

            double minX = brounds[0];
            double minY = brounds[1];
            double maxX = brounds[2];
            double maxY = brounds[3];

            //创建grid矩阵 将数据写进矩阵里
            Interpolate.createGridXY_Num(minX, minY, maxX, maxY, _X, _Y);

            double[][] _gridData = new double[width][height];

            int nc = dataInterval.length;

            _gridData = Interpolate.interpolation_IDW_Neighbor(trainData,
                    _X, _Y, 12, _undefData);// IDW插值

            int[][] S1 = new int[_gridData.length][_gridData[0].length];
            List<Border> _borders = Contour.tracingBorders(_gridData, _X, _Y,
                    S1, _undefData);

            cPolylineList = Contour.tracingContourLines(_gridData, _X, _Y, nc,
                    dataInterval, _undefData, _borders, S1);// 生成等值线

            cPolylineList = Contour.smoothLines(cPolylineList);// 平滑

            cPolygonList = Contour.tracingPolygons(_gridData, cPolylineList,
                    _borders, dataInterval);

            geojsonpogylon = getPolygonGeoJson(cPolygonList);


            System.out.println(geojsonpogylon);
//            if (isclip) {
//                polygonCollection = GeoJSONUtil.readGeoJsonByString(geojsonpogylon);
//                FeatureSource dc = clipFeatureCollection(fc, polygonCollection);
//                geojsonpogylon = getPolygonGeoJson(dc.getFeatures());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return geojsonpogylon;
    }

//    private FeatureSource clipFeatureCollection(FeatureCollection fc,
//                                                SimpleFeatureCollection gs) {
//        FeatureSource cs = null;
//        try {
//            List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
//            FeatureIterator contourFeatureIterator = gs.features();
//            FeatureIterator dataFeatureIterator = fc.features();
//            while (dataFeatureIterator.hasNext()) {
//                Feature dataFeature = dataFeatureIterator.next();
//                Geometry dataGeometry = (Geometry) dataFeature.getProperty(
//                        "the_geom").getValue();
//                while (contourFeatureIterator.hasNext()) {
//                    Feature contourFeature = contourFeatureIterator.next();
//                    Geometry contourGeometry = (Geometry) contourFeature
//                            .getProperty("geometry").getValue();
//                    double lv = (Double) contourFeature.getProperty("lvalue")
//                            .getValue();
//                    double hv = (Double) contourFeature.getProperty("hvalue")
//                            .getValue();
//                    if (dataGeometry.intersects(contourGeometry)) {
//                        Geometry geo = dataGeometry
//                                .intersection(contourGeometry);
//                        Map<String, Object> map = new HashMap<String, Object>();
//                        map.put("the_geom", geo);
//                        map.put("lvalue", lv);
//                        map.put("hvalue", hv);
//                        values.add(map);
//                    }
//
//                }
//
//            }
//
//            contourFeatureIterator.close();
//            dataFeatureIterator.close();
//
//            SimpleFeatureCollection sc = FeaureUtil
//                    .creatSimpleFeatureByFeilds(
//                            "polygons",
//                            "crs:4326,the_geom:MultiPolygon,lvalue:double,hvalue:double",
//                            values);
//            cs = FeaureUtil.creatFeatureSourceByCollection(sc);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return cs;
//        }
//
//        return cs;
//    }
//
    private String getPolygonGeoJson(FeatureCollection fc) {
//        FeatureJSON fjson = new FeatureJSON();
        StringBuffer sb = new StringBuffer();
        try {
            sb.append("{\"type\": \"FeatureCollection\",\"features\": ");
            FeatureIterator itertor = fc.features();
            List<String> list = new ArrayList<String>();
            while (itertor.hasNext()) {
                SimpleFeature feature = (SimpleFeature) itertor.next();
                StringWriter writer = new StringWriter();
//                fjson.writeFeature(feature, writer);
                System.out.println(feature.toString());
                list.add(writer.toString());
            }
            itertor.close();
            sb.append(list.toString());
            sb.append("}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
//
    private String getPolygonGeoJson(List<Polygon> cPolygonList) {

        StringBuffer geobody = new StringBuffer();
        StringBuffer geo = new StringBuffer();
        String geometry = " { \"type\":\"Feature\",\"geometry\":";
        String properties = ",\"properties\":{ \"hvalue\":";

        String head = "{\"type\": \"FeatureCollection\"," + "\"features\": [";
        String end = "  ] }";
        if (cPolygonList == null || cPolygonList.size() == 0) {
            return null;
        }
        try {
            for (Polygon pPolygon : cPolygonList) {

                List<Object> ptsTotal = new ArrayList<Object>();
                List<Object> pts = new ArrayList<Object>();

                PolyLine pline = pPolygon.OutLine;

                for (PointD ptD : pline.PointList) {
                    List<Double> pt = new ArrayList<Double>();
                    pt.add(ptD.X);
                    pt.add(ptD.Y);
                    pts.add(pt);
                }

                ptsTotal.add(pts);

                if (pPolygon.HasHoles()) {
                    for (PolyLine cptLine : pPolygon.HoleLines) {
                        List<Object> cpts = new ArrayList<Object>();
                        for (PointD ccptD : cptLine.PointList) {
                            List<Double> pt = new ArrayList<Double>();
                            pt.add(ccptD.X);
                            pt.add(ccptD.Y);
                            cpts.add(pt);
                        }
                        if (cpts.size() > 0) {
                            ptsTotal.add(cpts);
                        }
                    }
                }

                JSONObject js = new JSONObject();
                js.put("type", "Polygon");
                js.put("coordinates", ptsTotal);
                double hv = pPolygon.HighValue;
                double lv = pPolygon.LowValue;

                if (hv == lv) {
                    if (pPolygon.IsClockWise) {
                        if (!pPolygon.IsHighCenter) {
                            hv = hv - 0.1;
                            lv = lv - 0.1;
                        }

                    } else {
                        if (!pPolygon.IsHighCenter) {
                            hv = hv - 0.1;
                            lv = lv - 0.1;
                        }
                    }
                } else {
                    if (!pPolygon.IsClockWise) {
                        lv = lv + 0.1;
                    } else {
                        if (pPolygon.IsHighCenter) {
                            hv = hv - 0.1;
                        }
                    }

                }

                geo.append( geometry);
                geo.append( js.toString());
                geo.append(  properties);
                geo.append(hv);
                geo.append( ", \"lvalue\":");
                geo.append(  lv);
                geo.append(  "} }," );
                geo.append(  geo);

            }
            if (geo.lastIndexOf(",")==geo.length()-1) {
                geo.deleteCharAt(geo.length()-1);
//                geo = geo.substring(0, geo.lastIndexOf(","));
            }

            geobody.append(head);
            geobody.append(geo).append(end);
        } catch (Exception e) {
            e.printStackTrace();
            return geobody.toString();
        }
        return geobody.toString();
    }

    public static void write2File(String filePath,String buf){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        EquiSurface equiSurface = new EquiSurface();
//        CommonMethod cm = new CommonMethod();

        double[] bounds = {114.65302633361816, 37.96665276184082,
                114.68662210644531, 37.987079333496094};

        double[][] trainData = new double[3][30000];
//        File srcDatafile = new File("I:\\WorkSource\\turfjs_demo\\srcdata.json");
       String jsonStr = FileUtil.ReadFile("I:\\WorkSource\\turfjs_demo\\srcdata.json");
        JSONArray jsonArray =  JSONObject.parseArray(jsonStr);
        JSONObject js ;
        for (int i = 0; i < jsonArray.size(); i++) {
            js = jsonArray.getJSONObject(i);
//            double x = bounds[0] + new Random().nextDouble() * (bounds[2] - bounds[0]),
//                    y = bounds[1] + new Random().nextDouble() * (bounds[3] - bounds[1]),
//                    v = 0 + new Random().nextDouble() * (45 - 0);
            trainData[0][i] = js.getDouble("x");
            trainData[1][i] = js.getDouble("x");
            trainData[2][i] = js.getDouble("x");
        }

        double[] dataInterval = new double[]{10,20, 25, 30, 35, 40, 45,50};


        int[] size = new int[]{1000, 1000};

        boolean isclip = true;

        try {
//            String strJson =
             String strJson =  equiSurface.calEquiSurface(trainData, dataInterval, size, bounds, isclip);
            String strFile = "I:\\WorkSource\\turfjs_demo\\data.json";
//            cm.append2File(strFile, strJson);
            write2File(strFile,strJson);
            System.out.println("差值成功, 共耗时" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
