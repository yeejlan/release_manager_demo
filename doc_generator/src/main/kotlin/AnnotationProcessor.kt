package doc_generator

import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic.Kind.ERROR
import javax.tools.Diagnostic.Kind.WARNING
import javax.tools.Diagnostic.Kind.MANDATORY_WARNING

import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.lang.model.type.TypeMirror
import javax.lang.model.type.MirroredTypeException
import javax.lang.model.element.*

import com.squareup.javapoet.*

import doc_generator.DocScan
import com.fasterxml.jackson.module.kotlin.*

private val objectMapper = jacksonObjectMapper()

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes(value = arrayOf(
	"doc_generator.DocScan"
	))
class AnnotationProcessor : AbstractProcessor() {

	private val _docgen = "doc_generator"
	private lateinit var _messager: Messager
	private lateinit var _elements: Elements
	private lateinit var _types: Types
	private lateinit var _filer: Filer
	private var _lastRound = false
	private var _foundSomething = false
	private var _docMap = HashMap<String, Any?>()

	@Synchronized override fun init(processingEnv: ProcessingEnvironment) {
		super.init(processingEnv)
		_messager = processingEnv.getMessager()
		_elements = processingEnv.getElementUtils()
		_types = processingEnv.getTypeUtils()
		_filer = processingEnv.getFiler()

	}

	private fun printMessage(msg: Any) {
		_messager.printMessage(MANDATORY_WARNING, ""+msg)
	}

	private fun printError(msg: Any) {
		_messager.printMessage(ERROR, ""+msg)
	}	

	override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
		_foundSomething = false

		if(annotations == null){
			return true
		}

		/*handle @DocScan begin*/
		for (ele in roundEnv.getElementsAnnotatedWith(DocScan::class.java)){
			if (ele.getKind() != ElementKind.CLASS){
				printError("@"+DocScan::class.java.getName() + " can only apply on class, incorrect usage on: "+ ele)
			}
			val classElement = ele as TypeElement
			for(action in classElement.getEnclosedElements()) {
				val oneDoc = HashMap<String, Any?>()
				val path = action.getAnnotation(Path::class.java)
				if(path != null) {
					oneDoc.put("Path", path.value)
				}
				val desc = action.getAnnotation(Desc::class.java)
				if(desc != null) {
					oneDoc.put("Desc", desc.value)
				}			
				val method = action.getAnnotation(Method::class.java)
				if(method != null) {
					oneDoc.put("Method", method.value)
				}
				val param = action.getAnnotation(Param::class.java)
				if(param != null) {
					oneDoc.put("Param", param.value)
				}
				val ret = action.getAnnotation(Return::class.java)
				if(ret != null) {
					oneDoc.put("Return", ret.value)
				}
				val success = action.getAnnotation(Success::class.java)
				if(success != null) {
					oneDoc.put("Success", success.value)
				}
				val failed = action.getAnnotation(Failed::class.java)
				if(failed != null) {
					oneDoc.put("Failed", failed.value)
				}
				if(!oneDoc.isEmpty()) {
					val groupName = classElement.getQualifiedName().toString()
					val actionName = action.getSimpleName().toString()
					val fullname =  groupName + "." + actionName
					oneDoc.put("Group", groupName)
					oneDoc.put("Name", actionName)
					_docMap.put(fullname, oneDoc)
				}
			}
			_foundSomething = true
		}
		/*handle @DocScan end*/

		if(!_foundSomething && !_lastRound){
			_lastRound = true
			writeStuff()
		}

		return true
	}

	private fun writeStuff(){
		writeDocProvider()
	}

	private fun writeDocProvider(){
		val _methodGet = MethodSpec.methodBuilder("JsonString")
			.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
			.returns(String::class.java)
			.addStatement("return jsonStr")
			.build()

		val _field = FieldSpec.builder(String::class.java, "jsonStr")
			.addModifiers(Modifier.PRIVATE, Modifier.FINAL, Modifier.STATIC)
			.initializer("\$S", objectMapper.writeValueAsString(_docMap))
			.build()

		val _class = TypeSpec
			.classBuilder("DocProvider")
			.addModifiers(Modifier.PUBLIC)
			.addField(_field)
			.addMethod(_methodGet)
			.build()

		val javaFile = JavaFile.builder("doc_generator", _class).build()
		javaFile.writeTo(_filer)
	}	
}

