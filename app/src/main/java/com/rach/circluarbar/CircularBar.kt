package com.rach.circluarbar

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rach.circluarbar.ui.theme.CircluarBarTheme
import com.rach.circluarbar.ui.theme.LightGray
import com.rach.circluarbar.ui.theme.LightOrange
import com.rach.circluarbar.ui.theme.Orange
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularBar(
    modifier: Modifier = Modifier,
    maxValue: Int = 100,
    minValue: Int = 0,
    initialValue:Int
) {



    val circleRadius by remember {
        mutableFloatStateOf(236f)
    }

    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    Box(
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val circleThickness = width / 40f
            circleCenter = Offset(x = width / 2f, height / 2f)

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Orange,
                        LightOrange
                    )
                ),
                radius = circleRadius,
                center = circleCenter
            )

            drawCircle(
                color = LightGray,
                style = Stroke(
                    width = circleThickness
                ),
                radius = circleRadius,
                center = circleCenter
            )

            drawArc(
                color = Orange,
                startAngle = 90f,
                sweepAngle = (360f / maxValue) * initialValue.toFloat(),
                useCenter = false,
                style = Stroke(
                    width = circleThickness,
                    cap = StrokeCap.Round
                ),
                topLeft = Offset(
                    x = (width - circleRadius * 2f) / 2f,
                    y = (height - circleRadius * 2f) / 2f
                ),
                size = Size(
                    width = circleRadius * 2f,
                    height = circleRadius * 2f
                )
            )

            val outerRadius = circleRadius + circleThickness / 2f
            val gap = 25f

            for (i in 0..(maxValue - minValue)) {
                val color = if (i < initialValue - minValue) Orange else Orange.copy(alpha = 0.3f)
                val angleInDegree = i * 360 / (maxValue - minValue).toFloat()
                val angleInRadian = angleInDegree * PI / 180f + PI / 2f

                val yAdjustment = cos(angleInDegree * PI / 180f) * gap
                val xAdjustment = -sin(angleInDegree * PI / 180f) * gap

                val start = Offset(
                    x = (outerRadius * cos(angleInRadian) + circleCenter.x + xAdjustment).toFloat(),
                    y = (outerRadius * sin(angleInRadian) + circleCenter.y + yAdjustment).toFloat()
                )

                val  end = Offset(
                    x = (outerRadius * cos(angleInRadian) + circleCenter.x + xAdjustment).toFloat(),
                    y =  (outerRadius * sin(angleInRadian) + circleCenter.y + circleThickness + yAdjustment).toFloat()
                )

                rotate(
                    degrees = angleInDegree,
                    pivot = start
                ){
                    drawLine(
                        color = color,
                        start = start,
                        end = end,
                        strokeWidth = 2.dp.toPx()
                    )
                }
            }

            drawContext.canvas.nativeCanvas.apply {
                drawIntoCanvas {
                    drawText(
                        "$initialValue %",
                        circleCenter.x,
                        circleCenter.y + 45.dp.toPx()/3f,
                        Paint().apply {
                            textSize = 38.sp.toPx()
                            textAlign = Paint.Align.CENTER
                            color = Color.White.toArgb()
                            isFakeBoldText = true
                        }
                    )
                }
            }

        }




    }
}

@AppPreview
@Composable
private fun Preview() {
    CircluarBarTheme {
        CircularBar(
            initialValue = 7
        )
    }
}