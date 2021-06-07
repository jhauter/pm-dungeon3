package de.fhbielefeld.pmdungeon.quibble.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.fhbielefeld.pmdungeon.quibble.file.DungeonResource;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceHandler;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceType;

public class UILayer
{
	private final Stage stage;
	
	private final List<UIElement> uiElements;
	
	protected int zIndex;
	
	UIManager uiManager;
	
	public UILayer()
	{
		this.stage = new Stage();
		this.uiElements = new ArrayList<>();
		this.stage.addListener(new UIManagerHandler());
	}
	
	/**
	 * Rendering/update method
	 */
	public void draw()
	{
		this.stage.act();
		this.stage.draw();
		for(int i = 0; i < this.uiElements.size(); ++i)
		{
			this.uiElements.get(i).draw(this.stage.getBatch());
		}
	}
	
	/**
	 * Should be called when the UILayer is removed and not used anymore.
	 */
	public final void dispose()
	{
		this.stage.dispose();
	}
	
	/**
	 * @return the LibGDX stage
	 */
	public final Stage getStage()
	{
		return this.stage;
	}
	
	/**
	 * Sets how far in the foreground this UILayer should be drawn.
	 * This must be done before adding the UILayer to the UIManager.
	 * Otherwise UIManager.zOrderChanged() must be called afterwards.
	 * @param zIndex new z index
	 */
	public void setZIndex(int zIndex)
	{
		this.zIndex = zIndex;
	}
	
	public final int getZIndex()
	{
		return this.zIndex;
	}
	
	public final UIManager getUIManager()
	{
		return this.uiManager;
	}
	
	/**
	 * Like PM dungeon lib's addText(). This method automatically adds the text on the screen.
	 * @param text the text as string
	 * @param font what font to use
	 * @param x x-position on the screen
	 * @param y y-position on the screen
	 * @return the new Label
	 */
	public final Label addText(String text, UIFonts font, float x, float y)
	{
		LabelStyle style = new LabelStyle();
		style.font = font.getFont();
		Label label = new Label(text, style);
		label.setPosition(x, y);
		this.stage.addActor(label);
		return label;
	}
	
	public final void removeText(Label textLabel)
	{
		textLabel.remove();
	}
	
	/**
	 * Adds an image to the screen. If the file cannot be loaded then an error is logged and this method returns null.
	 * @param path file path to the image
	 * @param x x-position of the image
	 * @param y y-position of the image
	 * @param width width of the image
	 * @param height height of the image
	 * @return the added image handle
	 */
	public final Image addImage(String path, float x, float y, float width, float height)
	{
		DungeonResource<Texture> res = ResourceHandler.requestResourceInstantly(path, ResourceType.TEXTURE);
		if(!res.isLoaded())
		{
			return null;
		}
		Image img = new Image(res.getResource());
		img.setBounds(x, y, width, height);
		this.stage.addActor(img);
		return img;
	}
	
	/**
	 * Adds an image to the screen.
	 * @param texture the texture to show
	 * @param x x-position of the image
	 * @param y y-position of the image
	 * @param width width of the image
	 * @param height height of the image
	 * @return the added image handle
	 */
	public final Image addImage(TextureRegion texture, float x, float y, float width, float height)
	{
		Image img = new Image(texture);
		img.setBounds(x, y, width, height);
		this.stage.addActor(img);
		return img;
	}
	
	public final void removeImage(Image image)
	{
		image.remove();
	}
	
	/**
	 * Adds a custom UI element to the screen.
	 * @param element the element to add
	 */
	public final void addUIElement(UIElement element)
	{
		if(this.uiElements.contains(element))
		{
			throw new IllegalArgumentException("cannot add the same UIElement twice");
		}
		if(element.getUILayer() != null)
		{
			throw new IllegalArgumentException("this UIElement is already added to another UILayer");
		}
		this.uiElements.add(element);
		element.uiLayer = this;
	}
	
	public final void removeUIElement(UIElement element)
	{
		element.onRemove();
		this.uiElements.remove(element);
		element.uiLayer = null;
	}
	
	public final void clearUIElements()
	{
		for(int i = 0; i < this.uiElements.size(); ++i)
		{
			this.uiElements.get(i).onRemove();
			this.uiElements.get(i).uiLayer = null;
		}
		this.uiElements.clear();
	}
	
	public final int getUIElementsSize()
	{
		return this.uiElements.size();
	}
	
	public final UIElement getUIElementByIndex(int index)
	{
		return this.uiElements.get(index);
	}
	
	public final Iterator<UIElement> getUIElements()
	{
		return this.uiElements.iterator();
	}
	
	private class UIManagerHandler extends ClickListener
	{
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
		{
			UIElement e;
			UIElement elementOnMouse = UILayer.this.uiManager.getElementOnMouse();
			for(int i = 0; i < uiElements.size(); ++i)
			{
				e = uiElements.get(i);
				if(x >= e.x && x <= e.x + e.width && y >= e.y && y <= e.y + e.height)
				{
					final boolean clickWithGrabbed = elementOnMouse != null && uiElements.get(i).onClickWithGrabbedElement(event, x, y, pointer, button, elementOnMouse);
					final boolean clickAsGrabbed = elementOnMouse != null && elementOnMouse.onClickAsGrabbedElement(event, x, y, pointer, button, uiElements.get(i));
					if(uiElements.get(i).onClick(event, x, y, pointer, button) || clickWithGrabbed || clickAsGrabbed)
					{
						return true;
					}
				}
			}
			
			//If a GDX button was pressed or something..
			//This is after the for loop, meaning all UIElements are processed first
			return event.getRelatedActor() != null;
		}
	}
}
