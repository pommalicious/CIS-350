package edu.upenn.cis350;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/*
 * This class is a View that you can include in your Activity.
 * It uses very basic 2D graphics to draw a square and a circle.
 * Ooooooohhhh, pretty colors.....
 */

public class ShapesView extends View {
	
	int square_color;
	float square_x;
	float square_y;
	
	int rect_color;
	float rect_x;
	float rect_y;
	
	// These are the offsets between the coordinates of a user's touch and the upper left corner of the shape
	float square_x_offset;
	float square_y_offset;
	
	float rect_x_offset;
	float rect_y_offset;
	
	// This tracks whether a user is dragging a shape
	boolean dragging_square = false;
	boolean dragging_rect = false;
	
	// you must implement these constructors!!
	public ShapesView(Context c) {
		super(c);
		square_color = Color.BLUE;
		square_x = 100;
		square_y = 100;
		rect_color = Color.GREEN;
		rect_x = 300;
		rect_y = 250;
	}
	public ShapesView(Context c, AttributeSet a) {
		super(c, a);
		square_color = Color.BLUE;
		square_x = 100;
		square_y = 100;
		rect_color = Color.GREEN;
		rect_x = 300;
		rect_y = 250;
	}
	
// This method is called when the View is displayed
	protected void onDraw(Canvas canvas) {
	
		// this is the "paintbrush"
		Paint paint = new Paint();

		// Draw the square
		// change the color
		paint.setColor(square_color);
		
		// draw a BLUE Rectangle with corners at (100, 100) and (150, 150) (i.e. the 50x50 square)
		canvas.drawRect(square_x, square_y, square_x + 50, square_y + 50, paint);
		
		// Now draw the rectangle (80x60)
		paint.setColor(rect_color);
		canvas.drawRect(rect_x, rect_y, rect_x + 80, rect_y + 60, paint);
		
		/*
		// set a shadow
		paint.setShadowLayer(10, 10, 10, Color.GREEN);
		
		// create a “bounding rectangle”
		RectF rect = new RectF(150, 150, 280, 280);
		// draw an oval in the bounding rectangle
		canvas.drawOval(rect, paint);
		*/
	} 
	
// Called when the ShapeView is touched
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			float touch_x = event.getX();
			float touch_y = event.getY();
			square_x_offset = touch_x - square_x;
			square_y_offset = touch_y - square_y;
			rect_x_offset = touch_x - rect_x;
			rect_y_offset = touch_y - rect_y;
			
			// We are assuming no overlap because the "snap correction" specced in the assignment.
			// Theoretically if they were overlapping you could move both at the same time by pressing
			// in the overlapping area.
			
			// If the touch is within the bounds of the 50x50 square
			if (square_x_offset >= 0 && square_x_offset <= 50 && square_y_offset >= 0 && square_y_offset <= 50) {
				square_color = Color.YELLOW;
				dragging_square = true;
				this.invalidate();
				return true;
			}
			// Or if the touch is within the bounds of the 80x60 rectangle
			if (rect_x_offset >= 0 && rect_x_offset <= 80 && rect_y_offset >= 0 && rect_y_offset <= 60) {
				rect_color = Color.CYAN;
				dragging_rect = true;
				this.invalidate();
				return true;
			}
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			// Check for overlaps and correct by snapping
			float shape_distance_x = square_x - rect_x;
			float shape_distance_y = square_y - rect_y;
			// square is up/left overlapping of rectangle
			if (shape_distance_x <= 0 && shape_distance_x >= -50 && shape_distance_y <= 0 && shape_distance_y >= -50) {
				if (dragging_square == true) {
					square_x = square_x - (51 + shape_distance_x);
					square_y = square_y - (51 + shape_distance_y);
				} else if (dragging_rect == true) {
					rect_x = rect_x + (51 + shape_distance_x);
					rect_y = rect_y + (51 + shape_distance_y);
				}
			}
			// square is down/left of rectangle
			if (shape_distance_x <= 0 && shape_distance_x >= -50 && shape_distance_y >= 0 && shape_distance_y <= 60) {
				if (dragging_square == true) {
					square_x = square_x - (51 + shape_distance_x);
					square_y = square_y + (61 - shape_distance_y);
				} else if (dragging_rect == true) {
					rect_x = rect_x + (51 + shape_distance_x);
					rect_y = rect_y - (61 - shape_distance_y);
				}
			}
			// square is up/right of rectangle
			if (shape_distance_x >= 0 && shape_distance_x <= 80 && shape_distance_y <= 0 && shape_distance_y >= -50) {
				if (dragging_square == true) {
					square_x = square_x + (81 - shape_distance_x);
					square_y = square_y - (51 + shape_distance_y);
				} else if (dragging_rect == true) {
					rect_x = rect_x - (81 - shape_distance_x);
					rect_y = rect_y + (51 + shape_distance_y);
				}
			}
			// square is down/right of rectangle
			else if (shape_distance_x >= 0 && shape_distance_x <= 80 && shape_distance_y >=0 && shape_distance_y <= 60) {
				if (dragging_square == true) {
					square_x = square_x + (81 - shape_distance_x);
					square_y = square_y + (61 - shape_distance_y);
				} else if (dragging_rect == true) {
					rect_x = rect_x - (81 - shape_distance_x);
					rect_y = rect_y - (61 - shape_distance_y);
				}
			}
			
			
			// Could be "smarter" about this but might as well just reset both shapes to default colors
			// And we know we're no longer dragging either
			square_color = Color.BLUE;
			rect_color = Color.GREEN;
			dragging_square = false;
			dragging_rect = false;
			
			this.invalidate();
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (dragging_square == true) {
				float touch_x = event.getX();
				float touch_y = event.getY();
				// Adjust for the fact that user isn't touching shape at its exact upper right corner (don't want shape "snapping" to finger)
				square_x = touch_x - square_x_offset;
				square_y = touch_y - square_y_offset;
				this.invalidate();
				return true;
			}
			if (dragging_rect == true) {
				float touch_x = event.getX();
				float touch_y = event.getY();
				// Adjust for the fact that user isn't touching shape at its exact upper right corner (don't want shape "snapping" to finger)
				rect_x = touch_x - rect_x_offset;
				rect_y = touch_y - rect_y_offset;
				this.invalidate();
				return true;
			}
		}
		return false;
	}
	

}
