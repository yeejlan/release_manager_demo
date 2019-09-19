package doc_generator

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class Param(
	val value: Array<String> = arrayOf()
)