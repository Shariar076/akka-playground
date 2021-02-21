package com.example

import spray.json.{DefaultJsonProtocol, DeserializationException, JsArray, JsNumber, JsObject, JsString, JsValue, JsonParser, RootJsonFormat, deserializationError, enrichAny}
import java.io.{BufferedReader, FileReader}


class SprayJsonExample {
    class Color(val name: String, val red: Int, val green: Int, val blue: Int)

//        object MyJsonProtocol extends DefaultJsonProtocol {
//            implicit object ColorJsonFormat extends RootJsonFormat[Color] {
//                def write(c: Color) =
//                    JsArray(JsString(c.name), JsNumber(c.red), JsNumber(c.green), JsNumber(c.blue))
//
//                def read(value: JsValue) = value match {
//                    case JsArray(Vector(JsString(name), JsNumber(red), JsNumber(green), JsNumber(blue))) =>
//                        new Color(name, red.toInt, green.toInt, blue.toInt)
//                    case _ => deserializationError("Color expected")
//                }
//            }
//        }
        object MyJsonProtocol extends DefaultJsonProtocol {
            implicit object ColorJsonFormat extends RootJsonFormat[Color] {
                def write(c: Color) = JsObject(
                    "name" -> JsString(c.name),
                    "red" -> JsNumber(c.red),
                    "green" -> JsNumber(c.green),
                    "blue" -> JsNumber(c.blue)
                )
                def read(value: JsValue) = {
                    value.asJsObject.getFields("name", "red", "green", "blue") match {
                        case Seq(JsString(name), JsNumber(red), JsNumber(green), JsNumber(blue)) =>
                            new Color(name, red.toInt, green.toInt, blue.toInt)
                        case _ => throw new DeserializationException("Color expected")
                    }
                }
            }
        }
        import MyJsonProtocol._

        val json = new Color("CadetBlue", 95, 158, 160).toJson
        println(json)
        val color = json.convertTo[Color]
        println(color)

        val source = """{ "cpus": 1, "memory": 256 }"""
        val jsonAst = JsonParser(source)
        import WorkerConfigJsonProtocol._
        val myObject = jsonAst.convertTo[WorkerConfig]
        println(s"${myObject.cpus}, ${myObject.memory}")
        val w = new BufferedReader(new FileReader("src/main/resources/worker_conf.json"))

    case class Config(cpus: Float, memory: Int)
    object ConfigJsonProtocol extends DefaultJsonProtocol {
        implicit val configFormat = jsonFormat2(Config)
    }
    val source2 = scala.io.Source.fromFile("src/main/resources/worker_conf.json")
    val lines = try source2.mkString finally source2.close()
    val yourJson = JsonParser(lines)
    var myJsObject = yourJson.asJsObject()

    var nextConfig : Config = null
    var isUpdated : Boolean = false
    //    val jsonMsg = JsonParser(""" {"data": {"cpus": 4, "memory": 256}} """).asJsObject()
    val jsonMsg = JsonParser(""" {"data": "08"} """).asJsObject()
//    import ConfigJsonProtocol._
//    jsonMsg.fields.get("data") match {
//        case Some(value) =>
//            println(value.convertTo[String])
//            myJsObject.fields.get(value.convertTo[String]) match {
//                case Some(conf) =>
//                    println(conf.toString())
//                    nextConfig = conf.convertTo[Config]
//                    println(nextConfig.cpus)
//                    isUpdated = true
//            }
//
//        case None => println("Fatal Error: data not found")
//    }
//    myJsObject = JsObject(myJsObject.fields + ("02" -> JsString("new tag")))
}
